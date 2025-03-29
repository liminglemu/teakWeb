package com.teak.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/4 21:45
 * @Project: teakWeb
 * @File: ServiceMonitorAspect.java
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class ServiceMonitorAspect {

    private static final ObjectMapper mapper = new ObjectMapper();
    @Pointcut("execution(public * com.teak.blog.service.*.*(..))")
    public void ServiceMonitor() {
    }

    @Around("ServiceMonitor()")
    public Object unifiedMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法签名记录
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        String fullMethodName = className + "::" + methodName;

        // 参数记录
        String params = mapper.writeValueAsString(joinPoint.getArgs());
        log.info("方法 [{}] 参数详情: {}", fullMethodName, params);

        // 性能监控
        long startTime = System.currentTimeMillis();
        long nanoStart = System.nanoTime();

        Object result = joinPoint.proceed();

        // 耗时计算
        long duration = System.currentTimeMillis() - startTime;
        long nanoCost = (System.nanoTime() - nanoStart) / 1_000_000;

        // 日志输出
        log.info("[性能监控] {}.{} 执行耗时: {}ms", className, methodName, duration);
        log.info("方法 [{}] 返回: {}", fullMethodName, mapper.writeValueAsString(result));

        // 超时告警
        if (nanoCost > 1000) {
            log.warn("{} 超时警告！耗时 {} ms", signature.toShortString(), nanoCost);
        }

        return result;
    }


    /*@Around("ServiceMonitor()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.info("[当前线程]{}|[性能监控] {}.{} 执行耗时: {}ms",
                Thread.currentThread().getName(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                duration);

        return result;
    }

    // 环绕增强逻辑
    @Around("ServiceMonitor()")
    public Object logMethodName(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        // 组合类名+方法名
        String className = signature.getDeclaringTypeName(); // 获取类全限定名
        String methodName = signature.getName();
        String fullMethodName = String.format("%s::%s", className, methodName);

        Object[] args = joinPoint.getArgs();
        String params = Arrays.stream(args)
                .map(arg -> ToStringBuilder.reflectionToString(arg, ToStringStyle.SHORT_PREFIX_STYLE))
                .collect(Collectors.joining(", "));
        log.info("方法 [{}] 参数详情: {}", fullMethodName, params);
        Object result = joinPoint.proceed();

        String resultJson = mapper.writeValueAsString(result);
        log.info("方法 [{}] 返回: {}", fullMethodName, resultJson);
        return result;
    }

    @Around("ServiceMonitor()")
    public Object performanceMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long cost = (System.nanoTime() - start) / 1_000_000;
            if (cost > 1000) { // 超过1秒告警
                log.warn("{} 超时！超时！超时！ {} ms", joinPoint.getSignature().toShortString(), cost);
            }
        }
    }*/
}
