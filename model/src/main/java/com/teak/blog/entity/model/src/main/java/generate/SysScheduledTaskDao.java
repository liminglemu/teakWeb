package com.teak.blog.entity.model.src.main.java.generate;

import com.teak.blog.entity.model.SysScheduledTask;
import org.springframework.stereotype.Repository;

@Repository
public interface SysScheduledTaskDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysScheduledTask record);

    int insertSelective(SysScheduledTask record);

    SysScheduledTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysScheduledTask record);

    int updateByPrimaryKey(SysScheduledTask record);
}