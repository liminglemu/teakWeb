package com.teak.blog.controller;

import com.teak.blog.entity.vo.DeviceFaultRecordsVo;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.DeviceFaultRecordsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/11 21:33
 * @Project: teakWeb
 * @File: DeviceFaultRecordsController.java
 * @Description:
 */
@RestController
@RequestMapping("/api/deviceFaultRecords")
public class DeviceFaultRecordsController {
    private final DeviceFaultRecordsService deviceFaultRecordsService;

    public DeviceFaultRecordsController(DeviceFaultRecordsService deviceFaultRecordsService) {
        this.deviceFaultRecordsService = deviceFaultRecordsService;
    }


    @GetMapping("/getDeviceFaultRecords")
    public GlobalResult getDeviceFaultRecords(@RequestParam String startTime, @RequestParam String endTime) {
        List<DeviceFaultRecordsVo> deviceFaultRecords = deviceFaultRecordsService.getDeviceFaultRecords(startTime, endTime);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("deviceFaultRecords", deviceFaultRecords);
        return GlobalResult.success(hashMap);
    }
}
