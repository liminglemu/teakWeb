package com.teak.blog.controller;

import com.teak.blog.entity.model.Category;
import com.teak.blog.entity.vo.CategoryVo;
import com.teak.blog.handler.GlobalExceptionHandler;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:33
 * @Project: teakWeb
 * @File: CategoryController.java
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public GlobalResult list(@RequestParam Long id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Category> list = categoryService.getListById(id);
        hashMap.put("channelList", list);
        return GlobalResult.success(hashMap);
    }

    @PostMapping("/addArticle")
    public GlobalResult addArticle(@RequestBody CategoryVo categoryVo) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryVo.getArticle(), category);
            category.setUserId(categoryVo.getUserId());
            categoryService.addArticle(category);
            hashMap.put("category", category);
            return GlobalResult.success(hashMap);
        } catch (Exception e) {
            new GlobalExceptionHandler().handleException(e);
        }
        log.info("添加分类失败");
        return null;
    }

    @PutMapping("/updateArticle")
    public GlobalResult updateArticle(@RequestBody CategoryVo categoryVo) {
        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryVo.getArticle(), category);

            category.setUpdateTime(null);
            categoryService.updateArticle(category);
            return GlobalResult.success(null);
        } catch (Exception e) {
            new GlobalExceptionHandler().handleException(e);
        }
        return null;
    }

    @DeleteMapping("/deleteArticle")
    public GlobalResult deleteArticle(@RequestBody Long id) {
        try {
            categoryService.removeById(id);
            return GlobalResult.success(null);
        } catch (Exception e) {
            new GlobalExceptionHandler().handleException(e);
        }
        return null;
    }
}
