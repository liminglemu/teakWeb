package com.teak.blog.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/11 21:44
 * @Project: teakWeb
 * @File: DeviceFaultRecordsVo.java
 * @Description:
 */
@Data
public class DeviceFaultRecordsVo {
    /**
     * 设备Id
     */
    private Long deviceId;

    /**
     * 硬件故障-平均工单滞留时间
     */
    private Long hdAvgRetention;
    /**
     * 硬件故障-最长工单滞留时间
     */
    private Long hdMaxRetention;
    /**
     * 硬件故障-平均修复时间
     */
    private Long hdAvgFix;
    /**
     * 硬件故障-最长故障时间
     */
    private Long hdMaxFault;
    /**
     * 软件故障-平均工单滞留时间
     */
    private Long sfAvgRetention;
    /**
     * 软件故障-最长工单滞留时间
     */
    private Long sfMaxRetention;
    /**
     *  软件故障-平均修复时间
     */
    private Long sfAvgFix;
    /**
     * 软件故障-最长故障时间
     */
    private Long sfMaxFault;

}
