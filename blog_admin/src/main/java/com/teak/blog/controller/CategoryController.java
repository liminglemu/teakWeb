package com.teak.blog.controller;

import com.teak.blog.handler.GlobalExceptionHandler;
import com.teak.blog.model.Category;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.CategoryService;
import com.teak.blog.service.UserDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final UserDbService userDbService;

    public CategoryController(CategoryService categoryService, UserDbService userDbService) {
        this.categoryService = categoryService;
        this.userDbService = userDbService;
    }

    @GetMapping("/list")
    public GlobalResult list(@RequestParam Long id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            List<Category> list = categoryService.getListById(id);
            hashMap.put("channelList", list);
            return new GlobalResult().ok(hashMap);
        } catch (Exception e) {
            new GlobalExceptionHandler().handleException(e);
        }
        log.info("获取栏目列表失败");
        return null;
    }
}
