package com.teak.blog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
    @Pointcut("execution(public * com.teak.blog.service.*.*(..))")
    public void ServiceMonitor() {
    }

    @Around("ServiceMonitor()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.info("[性能监控] {}.{} 执行耗时: {}ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                duration);

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
    }
}
