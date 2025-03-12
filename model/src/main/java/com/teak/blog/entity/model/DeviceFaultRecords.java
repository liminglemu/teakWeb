package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/11 21:35
 * @Project: teakWeb
 * @File: DeviceFaultRecords.java
 * @Description:
 */
@Data
@TableName(value = "device_fault_records")
public class DeviceFaultRecords implements Serializable {
    /**
     * 主键ID（非自增，需手动赋值）
     * 匹配字段：record_id BIGINT(20)
     */
    @TableId(value = "record_id")
    private Long recordId;

    /**
     * 故障ID（业务唯一标识）
     * 匹配字段：fault_id VARCHAR(64)
     */
    @TableField(value = "fault_id")
    private String faultId;

    /**
     * 设备ID
     * 匹配字段：device_id BIGINT(20)
     */
    @TableField(value = "device_id")
    private Long deviceId;

    /**
     * 故障类型枚举（1-硬件，2-软件）
     * 匹配字段：fault_type INT(11)
     */
    @TableField(value = "fault_type")
    private Integer faultType;

    /**
     * 故障级别枚举（1-低，2-中，3-高）
     * 匹配字段：fault_level INT(11)
     */
    @TableField(value = "fault_level")
    private Integer faultLevel;

    /**
     * 事件发生时间（含毫秒精度）
     * 匹配字段：occur_time DATETIME(3)
     */
    @TableField(value = "occur_time")
    private Date occurTime;

    /**
     * 操作类型枚举（1-故障产生，2-派单，3-修复）
     * 匹配字段：op_type INT(11)
     */
    @TableField(value = "op_type")
    private Integer opType;
}
