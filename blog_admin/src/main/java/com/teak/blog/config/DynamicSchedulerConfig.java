package com.teak.blog.config;

import com.teak.blog.entity.model.SysScheduledTask;
import com.teak.blog.service.SysScheduledTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/5/4 00:01
 * @Project: teakWeb
 * @File: DynamicSchedulerConfig.java
 * @Description:
 */
@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DynamicSchedulerConfig implements SchedulingConfigurer {

    private final SysScheduledTaskService sysScheduledTaskService;

    private final ApplicationContext applicationContext;

    private final ThreadPoolTaskScheduler scheduler;

    private final ExecutorService executorService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setTaskScheduler(scheduler);
        List<SysScheduledTask> tasks = sysScheduledTaskService.findByStatus(1);
        tasks.forEach(task -> registrar.addTriggerTask(
                createRunnable(task),
                createTrigger(task)
        ));
    }

    private Runnable createRunnable(SysScheduledTask task) {
        return () -> CompletableFuture.runAsync(() -> {
            try {
                log.info("执行任务[{}]", task.getTaskName());
                Object bean = applicationContext.getBean(task.getBeanName());
                Method method = bean.getClass().getMethod(task.getMethodName(), String.class);
                method.invoke(bean, task.getParams());
            } catch (Exception e) {
                log.error("执行任务[{}]异常: {}", task.getTaskName(), e.getMessage());
            }
        }, executorService);
    }

    private Trigger createTrigger(SysScheduledTask task) {
        return triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger(task.getCronExpression());
            return cronTrigger.nextExecution(triggerContext);
        };
    }
}
