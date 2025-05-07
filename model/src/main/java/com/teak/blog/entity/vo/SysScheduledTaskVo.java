package com.teak.blog.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/4/19 16:37
 * @Project: teakWeb
 * @File: SysScheduledTaskVo.java
 * @Description:
 */
@Data
public class SysScheduledTaskVo {
    /**
     * 任务唯一标识
     */
    private String taskName;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * Spring Bean名称
     */
    private String beanName;

    /**
     * 执行方法
     */
    private String methodName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 方法参数 list内部必须承载的都是可序列化对象
     */
    private List<? extends Serializable> params;

    /**
     * 方法参数类型数组，用逗号分隔例如：String.class,Integer.class
     */
    private String parameterTypes;
}
