package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.mapper.CategoryMapper;
import com.teak.blog.model.Category;
import com.teak.blog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/25 21:48
 * @Project: teakWeb
 * @File: CategoryServiceImp.java
 * @Description:
 */
@Slf4j
@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryServiceImp(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getListById(Long id) {
        return categoryMapper.getListById(id);
    }

    @Override
    public void addArticle(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void updateArticle(Category category) {
        categoryMapper.updateById(category);
    }

}
