package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.entity.model.SysScheduledTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/4/19 10:21
 * @Project: teakWeb
 * @File: SysScheduledTaskMapper.java
 * @Description:
 */
@Repository
public interface SysScheduledTaskMapper extends BaseMapper<SysScheduledTask> {
    List<SysScheduledTask> getAllTask();

    List<SysScheduledTask> findByStatus(int i);
}
