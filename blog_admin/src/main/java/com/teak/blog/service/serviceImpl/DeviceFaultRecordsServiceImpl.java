package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.DeviceFaultRecords;
import com.teak.blog.entity.vo.DeviceFaultRecordsVo;
import com.teak.blog.mapper.DeviceFaultRecordsMapping;
import com.teak.blog.service.DeviceFaultRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/11 21:38
 * @Project: teakWeb
 * @File: DeviceFaultRecordsServiceImpl.java
 * @Description:
 */
@Slf4j
@Service
public class DeviceFaultRecordsServiceImpl extends ServiceImpl<DeviceFaultRecordsMapping, DeviceFaultRecords> implements DeviceFaultRecordsService {
    private final DeviceFaultRecordsMapping deviceFaultRecordsMapping;

    public DeviceFaultRecordsServiceImpl(DeviceFaultRecordsMapping deviceFaultRecordsMapping) {
        this.deviceFaultRecordsMapping = deviceFaultRecordsMapping;
    }

    @Override
    public List<DeviceFaultRecordsVo> getDeviceFaultRecords(String startTime, String endTime) {
        Date endDateParse;
        try {
            endDateParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<DeviceFaultRecords> deviceFaultRecords = deviceFaultRecordsMapping.getDeviceFaultRecordsByTime(startTime, endTime);

        ConcurrentHashMap<Long, DeviceFaultRecordsVo> hashMap = new ConcurrentHashMap<>();

        deviceFaultRecords.stream().collect(Collectors.groupingBy(DeviceFaultRecords::getDeviceId)).forEach(
                (deviceId, deviceFaultRecords1) -> {
                    /*硬件故障分组*/
                    deviceFaultRecords1.stream().collect(Collectors.groupingBy(DeviceFaultRecords::getFaultType)).forEach(
                            (faultType, deviceFaultRecords2) -> {
                                if (faultType == 1) {
                                    /*工单滞留时间list*/
                                    ArrayList<Long> backlogOfWorkOrdersList = new ArrayList<>();
                                    /*修复时间list*/
                                    ArrayList<Long> repairCompletionTimeList = new ArrayList<>();
                                    /*故障时间list*/
                                    ArrayList<Long> faultUnfinishedTimeList = new ArrayList<>();
                                    deviceFaultRecords2.stream().collect(Collectors.groupingBy(DeviceFaultRecords::getFaultId)).forEach(
                                            (faultId, deviceFaultRecords3) -> {

                                                /*故障发生时间*/
                                                Date timeOfFailure = null;
                                                /*开始派单时间*/
                                                Date startDispatchingTime = null;
                                                /*维修结束时间*/
                                                Date repairCompletionTime = null;
                                                for (DeviceFaultRecords records : deviceFaultRecords3) {
                                                    if (records.getOpType() == 1) {
                                                        timeOfFailure = records.getOccurTime();
                                                    }
                                                    if (records.getOpType() == 2) {
                                                        startDispatchingTime = records.getOccurTime();
                                                    }
                                                    if (records.getOpType() == 3) {
                                                        repairCompletionTime = records.getOccurTime();
                                                    }
                                                }
                                                if (deviceFaultRecords3.size() == 1) {
                                                    backlogOfWorkOrdersList.add(endDateParse.getTime() - timeOfFailure.getTime());
                                                    faultUnfinishedTimeList.add(endDateParse.getTime() - timeOfFailure.getTime());
                                                }
                                                if (deviceFaultRecords3.size() == 2) {
                                                    backlogOfWorkOrdersList.add(startDispatchingTime.getTime() - timeOfFailure.getTime());
                                                    faultUnfinishedTimeList.add(endDateParse.getTime() - timeOfFailure.getTime());
                                                }
                                                if (deviceFaultRecords3.size() == 3) {
                                                    backlogOfWorkOrdersList.add(startDispatchingTime.getTime() - timeOfFailure.getTime());
                                                    repairCompletionTimeList.add(repairCompletionTime.getTime() - startDispatchingTime.getTime());
                                                    faultUnfinishedTimeList.add(repairCompletionTime.getTime() - timeOfFailure.getTime());
                                                }
                                            }
                                    );

                                    DeviceFaultRecordsVo deviceFaultRecordsVo;
                                    if (hashMap.containsKey(deviceId)) {
                                        deviceFaultRecordsVo = hashMap.get(deviceId);
                                    } else {
                                        deviceFaultRecordsVo = new DeviceFaultRecordsVo();
                                        deviceFaultRecordsVo.setDeviceId(deviceId);
                                    }
                                    /*平均滞留时间*/
                                    backlogOfWorkOrdersList.stream().reduce(Long::sum).ifPresent(sum -> {
                                        Long averageResidenceTime = sum / backlogOfWorkOrdersList.size();
                                        deviceFaultRecordsVo.setHdAvgRetention(averageResidenceTime);
                                    });
                                    /*最大滞留时间*/
                                    backlogOfWorkOrdersList.stream().max(Long::compare).ifPresent(deviceFaultRecordsVo::setHdMaxRetention);
                                    /*平均修复时间*/
                                    repairCompletionTimeList.stream().reduce(Long::sum).ifPresent(sum -> {
                                        Long meanTimeToRepair = sum / repairCompletionTimeList.size();
                                        deviceFaultRecordsVo.setHdAvgFix(meanTimeToRepair);
                                    });
                                    /*最长故障时间*/
                                    faultUnfinishedTimeList.stream().max(Long::compare).ifPresent(deviceFaultRecordsVo::setHdMaxFault);

                                    hashMap.put(deviceId, deviceFaultRecordsVo);
                                } else if (faultType == 2) {
                                    /*工单滞留时间list*/
                                    ArrayList<Long> backlogOfWorkOrdersList = new ArrayList<>();
                                    /*修复时间list*/
                                    ArrayList<Long> repairCompletionTimeList = new ArrayList<>();
                                    /*故障时间list*/
                                    ArrayList<Long> faultUnfinishedTimeList = new ArrayList<>();
                                    deviceFaultRecords2.stream().collect(Collectors.groupingBy(DeviceFaultRecords::getFaultId)).forEach(
                                            (faultId, deviceFaultRecords3) -> {

                                                /*故障发生时间*/
                                                Date timeOfFailure = null;
                                                /*开始派单时间*/
                                                Date startDispatchingTime = null;
                                                /*维修结束时间*/
                                                Date repairCompletionTime = null;
                                                for (DeviceFaultRecords records : deviceFaultRecords3) {
                                                    if (records.getOpType() == 1) {
                                                        timeOfFailure = records.getOccurTime();
                                                    }
                                                    if (records.getOpType() == 2) {
                                                        startDispatchingTime = records.getOccurTime();
                                                    }
                                                    if (records.getOpType() == 3) {
                                                        repairCompletionTime = records.getOccurTime();
                                                    }
                                                }
                                                if (deviceFaultRecords3.size() == 1) {
                                                    backlogOfWorkOrdersList.add(endDateParse.getTime() - timeOfFailure.getTime());
                                                    faultUnfinishedTimeList.add(endDateParse.getTime() - timeOfFailure.getTime());
                                                }
                                                if (deviceFaultRecords3.size() == 2) {
                                                    backlogOfWorkOrdersList.add(startDispatchingTime.getTime() - timeOfFailure.getTime());
                                                    faultUnfinishedTimeList.add(endDateParse.getTime() - timeOfFailure.getTime());
                                                }
                                                if (deviceFaultRecords3.size() == 3) {
                                                    backlogOfWorkOrdersList.add(startDispatchingTime.getTime() - timeOfFailure.getTime());
                                                    repairCompletionTimeList.add(repairCompletionTime.getTime() - startDispatchingTime.getTime());
                                                    faultUnfinishedTimeList.add(repairCompletionTime.getTime() - timeOfFailure.getTime());
                                                }
                                            }
                                    );
                                    DeviceFaultRecordsVo deviceFaultRecordsVo;
                                    if (hashMap.containsKey(deviceId)) {
                                        deviceFaultRecordsVo = hashMap.get(deviceId);
                                    } else {
                                        deviceFaultRecordsVo = new DeviceFaultRecordsVo();
                                        deviceFaultRecordsVo.setDeviceId(deviceId);
                                    }
                                    /*平均滞留时间*/
                                    backlogOfWorkOrdersList.stream().reduce(Long::sum).ifPresent(sum -> {
                                        Long averageResidenceTime = sum / backlogOfWorkOrdersList.size();
                                        deviceFaultRecordsVo.setSfAvgRetention(averageResidenceTime);
                                    });
                                    /*最大滞留时间*/
                                    backlogOfWorkOrdersList.stream().max(Long::compare).ifPresent(deviceFaultRecordsVo::setSfMaxRetention);
                                    /*平均修复时间*/
                                    repairCompletionTimeList.stream().reduce(Long::sum).ifPresent(sum -> {
                                        Long meanTimeToRepair = sum / repairCompletionTimeList.size();
                                        deviceFaultRecordsVo.setSfAvgFix(meanTimeToRepair);
                                    });
                                    /*最长故障时间*/
                                    faultUnfinishedTimeList.stream().max(Long::compare).ifPresent(deviceFaultRecordsVo::setSfMaxFault);

                                    hashMap.put(deviceId, deviceFaultRecordsVo);
                                }
                            }
                    );
                }
        );
        // 新增排序逻辑（在最终return之前）
        List<DeviceFaultRecordsVo> sortedList = hashMap.entrySet().stream()
                .sorted((o1, o2) -> {
                    return Long.compare(o1.getKey(), o2.getKey());
                })  // 按Long键自然顺序排序
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        for (DeviceFaultRecordsVo deviceFaultRecordsVo : sortedList) {
            log.info(deviceFaultRecordsVo.toString());
        }
        return sortedList;
    }
}
