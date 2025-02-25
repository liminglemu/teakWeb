package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.model.Category;
import com.teak.blog.model.UserDb;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/25 21:47
 * @Project: teakWeb
 * @File: CategoryMapper.java
 * @Description:
 */
@Repository
public interface CategoryMapper  extends BaseMapper<Category> {
    List<Category> getListById(Long id);
}
