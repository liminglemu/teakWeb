package com.teak.blog.config;

import com.teak.blog.annotation.CreateTime;
import com.teak.blog.annotation.SnowflakeAlgorithm;
import com.teak.blog.annotation.UpdateTime;
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("触发审计拦截器");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];

        if (param == null) {
            return invocation.proceed();
        }

        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        boolean isInsert = sqlCommandType == SqlCommandType.INSERT;
        boolean isUpdate = sqlCommandType == SqlCommandType.UPDATE;

        for (Field field : getCachedFields(param.getClass())) {
            try {
                processField(field, param, isInsert, isUpdate);
            } catch (IllegalAccessException e) {
                log.error("字段[{}]赋值失败: {}", field.getName(), e.getMessage());
            }
        }
        return invocation.proceed();
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
        } finally {
            if (needRestoreAccess) {
                field.setAccessible(false);
            }
        }
    }

    private void handleSnowflakeAlgorithm(Field field, Object param) throws IllegalAccessException {
        if (field.get(param) == null) {
            log.debug("注入Snowflake ID");
            field.set(param, idWorker.nextId());
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

    private boolean prepareFieldAccess(Field field, Object param) {
        boolean needSpecialAccess = (!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.canAccess(param);

        if (needSpecialAccess) {
            field.setAccessible(true);
        }
        return needSpecialAccess;
    }

    private static List<Field> getCachedFields(Class<?> clazz) {
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
