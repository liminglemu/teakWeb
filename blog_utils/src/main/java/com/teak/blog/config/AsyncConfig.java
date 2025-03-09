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
//    @Bean("executorService")
    public ExecutorService getAsyncExecutor() {
        // 定义线程池
        // 核心线程数
        int corePoolSize = 6;
        // 最大线程数
        int maximumPoolSize = 30;
        // 闲置线程存活时间
        long keepAliveTime = 200;
        // 时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        int queueCapacity = 100;
        // 线程队列
        ThreadFactory threadFactory = new CustomizableThreadFactory("AsyncPool-");

        RejectedExecutionHandler handler = (r, executor) -> {
            log.warn("任务 {} 拒收", r.toString());
            new ThreadPoolExecutor.CallerRunsPolicy().rejectedExecution(r, executor);
        };

        executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new ArrayBlockingQueue<>(queueCapacity),
                threadFactory,
                handler
        );

        executor.allowCoreThreadTimeOut(true);
        return TtlExecutors.getTtlExecutorService(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("异步方法 {} 失败", method.getName(), ex);
    }


    @PreDestroy
    public void destroy() {
        // 直接操作成员变量，避免触发Bean获取
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
