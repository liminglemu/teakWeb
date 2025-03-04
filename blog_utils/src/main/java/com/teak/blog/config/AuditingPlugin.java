package com.teak.blog.config;

import com.teak.blog.annotation.*;
import com.teak.blog.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/16 01:08
 * @Project: teakWeb
 * @File: AuditingPlugin.java
 * @Description:
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component
public class AuditingPlugin implements Interceptor {

    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();
    private final IdWorker idWorker;

    public AuditingPlugin(IdWorker idWorker) {
        this.idWorker = idWorker;
    }

    private static List<Field> getCachedFields(Class<?> clazz) {
        synchronized (CLASS_FIELD_CACHE) {
            return CLASS_FIELD_CACHE.computeIfAbsent(clazz, k -> {
                List<Field> fields = new ArrayList<>();
                Class<?> currentClass = clazz;
                while (currentClass != null && currentClass != Object.class) {
                    Collections.addAll(fields, currentClass.getDeclaredFields());
                    currentClass = currentClass.getSuperclass();
                }
                return Collections.unmodifiableList(fields);
            });
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("当前事务状态: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.debug("触发审计拦截器");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];

        if (param == null) return invocation.proceed();

        processByCommandType(mappedStatement.getSqlCommandType(), param);
        return invocation.proceed();
    }

    private void processByCommandType(SqlCommandType commandType, Object param) {
        if (commandType == SqlCommandType.INSERT) {
            processFieldsWithAnnotations(param, true, false);
        } else if (commandType == SqlCommandType.UPDATE) {
            Object targetObject = extractTargetObject(param);
            if (targetObject == null) {
                log.warn("UPDATE操作中未找到实体对象，param: {}", param);
                return;
            }
            processFieldsWithAnnotations(targetObject, false, true);
        }
    }

    private Object extractTargetObject(Object param) {
        Object targetObject;
        if (param instanceof Map) {
            Map<?, ?> paramMap = (Map<?, ?>) param;
            if (paramMap.containsKey("et")) {
                targetObject = paramMap.get("et");
                if (targetObject == null) {
                    for (Object value : paramMap.values()) {
                        if (value != null && !(value instanceof Map)) {
                            targetObject = value;
                            break;
                        }
                    }
                }
                return targetObject;
            }
        }
        return param;
    }

    private void processFieldsWithAnnotations(Object target, boolean isInsert, boolean isUpdate) {
        getCachedFields(target.getClass()).forEach(field -> {
            try {
                processField(field, target, isInsert, isUpdate);
            } catch (IllegalAccessException e) {
                log.error("字段[{}]赋值失败: {}", field.getName(), e.getMessage(), e);
            }
        });
    }

    private void processField(Field field, Object param, boolean isInsert, boolean isUpdate) throws IllegalAccessException {

        boolean needRestoreAccess = false;
        try {
            if (field.isAnnotationPresent(SnowflakeAlgorithm.class) && isInsert) {
                needRestoreAccess = prepareFieldAccess(field, param);
                handleSnowflakeAlgorithm(field, param);
            }

            if (field.isAnnotationPresent(CreateTime.class) && isInsert) {
                needRestoreAccess = prepareFieldAccess(field, param);
                handleCreateTime(field, param);
            }

            if (field.isAnnotationPresent(UpdateTime.class)) {
                needRestoreAccess = prepareFieldAccess(field, param);
                handleUpdateTime(field, param, isInsert, isUpdate);
            }

            if (field.isAnnotationPresent(Statue.class) && isInsert) {
                needRestoreAccess = prepareFieldAccess(field, param);
                handleStatue(field, param);
            }
            if (field.isAnnotationPresent(IsDeleted.class) && isInsert) {
                needRestoreAccess = prepareFieldAccess(field, param);
                handleIsDeleted(field, param);
            }
        } finally {
            if (needRestoreAccess) {
                field.setAccessible(false);
            }
        }
    }

    private void handleSnowflakeAlgorithm(Field field, Object param) throws IllegalAccessException {
        if (field.get(param) == null) {
            log.debug("注入Snowflake ID");
            // 确保字段类型匹配
            if (field.getType().equals(Long.class)) {
                field.set(param, idWorker.nextId());
            } else {
                log.warn("字段[{}]类型不匹配，需要Long类型", field.getName());
            }
        }
    }

    private void handleCreateTime(Field field, Object param) throws IllegalAccessException {
        if (field.get(param) == null) {
            log.debug("设置创建时间");
            field.set(param, new Date());
        }
    }

    private void handleUpdateTime(Field field, Object param, boolean isInsert, boolean isUpdate) throws IllegalAccessException {
        if ((isInsert || isUpdate) && field.get(param) == null) {
            log.debug("{} 更新时间", isInsert ? "INSERT" : "UPDATE");
            field.set(param, new Date());
        }
    }

    private void handleStatue(Field field, Object param) throws IllegalAccessException {
        if (field.get(param) == null) {
            log.debug("注入Statue 默认值0");
            field.set(param, 0);
        }
    }

    private void handleIsDeleted(Field field, Object param) throws IllegalAccessException {
        if (field.get(param) == null) {
            log.debug("注入IsDeleted 默认值0");
            field.set(param, 0);
        }
    }

    private boolean prepareFieldAccess(Field field, Object param) {
        /*boolean needSpecialAccess = (!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.canAccess(param);

        if (needSpecialAccess) {
            field.setAccessible(true);
        }
        return needSpecialAccess;*/
        if (!field.canAccess(param)) {
            field.setAccessible(true);
            return true;
        }
        return false;
    }
}
