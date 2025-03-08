package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.entity.model.Category;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/25 21:48
 * @Project: teakWeb
 * @File: CategoryService.java
 * @Description:
 */
public interface CategoryService extends IService<Category> {
    List<Category> getListById(Long id);

    void addArticle(Category category);

    void updateArticle(Category category);

    Category getByCateName(String category, Long userId);
}
