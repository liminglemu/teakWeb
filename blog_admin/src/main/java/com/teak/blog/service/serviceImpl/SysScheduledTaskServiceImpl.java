package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teak.blog.entity.model.SysScheduledTask;
import com.teak.blog.entity.vo.SysScheduledTaskVo;
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
    public void addScheduledTask(SysScheduledTaskVo sysScheduledTaskVo) {
        SysScheduledTask scheduledTask = new SysScheduledTask();
        teakUtils.copyProperties(sysScheduledTaskVo, scheduledTask);
        scheduledTask.setBeanName(teakUtils.lowerFirstCharAndTrim(sysScheduledTaskVo.getBeanName()));
        scheduledTask.setMethodName(teakUtils.lowerFirstCharAndTrim(sysScheduledTaskVo.getMethodName()));
        scheduledTask.setParameterTypes(teakUtils.resolveReferenceClassName(sysScheduledTaskVo.getParameterTypes()));
        if (sysScheduledTaskVo.getParams() != null) {
            try {
                scheduledTask.setParams(new ObjectMapper().writeValueAsString(sysScheduledTaskVo.getParams()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        sysScheduledTaskMapper.insert(scheduledTask);
    }

    @Override
    public List<SysScheduledTask> findByStatus(int i) {
        return sysScheduledTaskMapper.findByStatus(i);
    }
}
