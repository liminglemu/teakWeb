package com.teak.blog.service;

import com.teak.blog.entity.model.SysScheduledTask;
import com.teak.blog.entity.vo.SysScheduledTaskVo;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/4/19 10:19
 * @Project: teakWeb
 * @File: SysScheduledTaskService.java
 * @Description:
 */
public interface SysScheduledTaskService {
    List<SysScheduledTask> getAllTask();

    void addScheduledTask(SysScheduledTaskVo sysScheduledTaskVo);

    List<SysScheduledTask>  findByStatus(int i);
}
