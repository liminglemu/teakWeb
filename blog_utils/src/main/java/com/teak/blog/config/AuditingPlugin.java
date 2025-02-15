package com.teak.blog.config;

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

    private final IdWorker idWorker;

    public AuditingPlugin(IdWorker idWorker) {
        this.idWorker = idWorker;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("拦截器");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (param != null) {
            if (sqlCommandType == SqlCommandType.INSERT) {
                Field field = param.getClass().getDeclaredField("id");
                boolean flag = (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                        || Modifier.isFinal(field.getModifiers())) && !field.isAccessible();
                if (flag) {
                    field.setAccessible(true);
                }
                field.set(param, idWorker.nextId());

            }
        }
        return invocation.proceed();
    }
}
