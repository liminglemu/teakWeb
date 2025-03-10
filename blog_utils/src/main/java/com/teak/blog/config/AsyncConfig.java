package com.teak.blog.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/2 22:32
 * @Project: teakWeb
 * @File: AsyncConfig.java
 * @Description:
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {
    private ThreadPoolExecutor executor;

    @Override
    @Bean("executorService")
    public ExecutorService getAsyncExecutor() {
        // 定义线程池
        // 核心线程数,设置为核心数量乘2，避免线程频繁创建销毁
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        // 最大线程数
        int maxPoolSize = 60;
        // 闲置线程存活时间
        long keepAliveSeconds = 200;
        // 队列容量
        int queueCapacity = 200;
        // 增强型线程工厂（含异常处理）
        ThreadFactory threadFactory = new CustomizableThreadFactory("AsyncPool-") {
            private final AtomicLong threadCount = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = super.newThread(r);
                thread.setUncaughtExceptionHandler((t, e) ->
                        log.error("异步线程[{}]执行异常: {}", t.getName(), e.getMessage(), e));
                thread.setName("AsyncPool-" + threadCount.getAndIncrement());
                return thread;
            }
        };

        // 拒绝策略（分级处理）
        RejectedExecutionHandler rejectionHandler = (runnable, executor) -> {
            if (executor.isShutdown()) {
                log.warn("线程池已关闭，拒绝新任务: {}", runnable);
                return;
            }

            log.warn("线程池过载，触发拒绝策略. ActiveThreads={}, QueueSize={}, TaskCount={}",
                    executor.getActiveCount(),
                    executor.getQueue().size(),
                    executor.getTaskCount());

            // 降级策略：转移到备用队列或记录错误
            // 此处示例：记录到Redis或发送告警
            log.error("任务被拒绝，请检查系统负载. Task: {}", runnable);
        };

        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),  // 更改为无界队列需谨慎
                threadFactory,
                rejectionHandler
        );

        // 核心线程预热
        executor.prestartAllCoreThreads();

        // TTL上下文传递（需确保依赖正确）
        return TtlExecutors.getTtlExecutorService(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("异步方法执行失败: {} [Params: {}]", method.getName(), params, ex);
        };
    }


    @PreDestroy
    public void gracefulShutdown() {
        log.info("开始关闭异步线程池...");
        executor.shutdown();  // 停止接收新任务

        try {
            // 分阶段关闭
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                log.warn("线程池未完全关闭，尝试强制终止");
                executor.shutdownNow().forEach(task ->
                        log.warn("被强制终止的任务: {}", task));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
        log.info("异步线程池已关闭");
    }
}
