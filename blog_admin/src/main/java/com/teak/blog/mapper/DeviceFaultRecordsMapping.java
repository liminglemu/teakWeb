package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.entity.model.DeviceFaultRecords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/11 21:41
 * @Project: teakWeb
 * @File: DeviceFaultRecordsMapping.java
 * @Description:
 */
@Repository
public interface DeviceFaultRecordsMapping extends BaseMapper<DeviceFaultRecords> {
    List<DeviceFaultRecords> getDeviceFaultRecordsByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
