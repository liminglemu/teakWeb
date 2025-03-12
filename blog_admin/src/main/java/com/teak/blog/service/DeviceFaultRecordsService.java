package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.entity.model.DeviceFaultRecords;
import com.teak.blog.entity.vo.DeviceFaultRecordsVo;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/11 21:34
 * @Project: teakWeb
 * @File: DeviceFaultRecordsService.java
 * @Description:
 */
public interface DeviceFaultRecordsService extends IService<DeviceFaultRecords> {
    List<DeviceFaultRecordsVo> getDeviceFaultRecords(String startTime, String endTime);
}
