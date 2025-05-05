package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.SysScheduledTask;
import com.teak.blog.mapper.SysScheduledTaskMapper;
import com.teak.blog.service.SysScheduledTaskService;
import com.teak.blog.utils.TeakUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/4/19 10:20
 * @Project: teakWeb
 * @File: SysScheduledTaskServiceImpl.java
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class SysScheduledTaskServiceImpl extends ServiceImpl<SysScheduledTaskMapper, SysScheduledTask> implements SysScheduledTaskService {

    private final SysScheduledTaskMapper sysScheduledTaskMapper;
    private final TeakUtils teakUtils;


    @Override
    public List<SysScheduledTask> getAllTask() {
        return sysScheduledTaskMapper.getAllTask();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addScheduledTask(SysScheduledTask sysScheduledTask) {
        SysScheduledTask scheduledTask = new SysScheduledTask();
        teakUtils.copyProperties(sysScheduledTask, scheduledTask);
        scheduledTask.setBeanName(teakUtils.lowerFirstCharAndTrim(scheduledTask.getBeanName()));
        scheduledTask.setMethodName(teakUtils.lowerFirstCharAndTrim(scheduledTask.getMethodName()));
        sysScheduledTaskMapper.insert(scheduledTask);
    }

    @Override
    public List<SysScheduledTask> findByStatus(int i) {
        return sysScheduledTaskMapper.findByStatus(i);
    }
}
