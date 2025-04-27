package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import com.teak.blog.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 动态定时任务表
 * sys_scheduled_task
 */
@Data
@TableName(value = "sys_scheduled_task")
@EqualsAndHashCode(callSuper = true)
@ToString
public class SysScheduledTask extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @SnowflakeAlgorithm
    private Long id;

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
     * 方法参数(JSON格式)
     */
    private String params;

}