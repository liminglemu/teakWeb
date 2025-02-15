package com.teak.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.teak.blog.model.HospitalMode;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The type Admin blog controller.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/18
 */
@Slf4j
@RestController
@RequestMapping("/api/blog")
public class AdminBlogController {

    private final BlogService blogService;

    /**
     * Instantiates a new Admin blog controller.
     *
     * @param blogService the blog service
     */
    public AdminBlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Gets page list.
     *
     * @param status the status
     * @return the page list
     */
    @GetMapping("/pageList/{status}")
    public GlobalResult getPageList(@PathVariable Integer status) {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        IPage<HospitalMode> hospitalModeIPage = blogService.getPage(1, 2, status);
        concurrentHashMap.put("pageList", hospitalModeIPage.getRecords());
        concurrentHashMap.put("total", hospitalModeIPage.getTotal());
        concurrentHashMap.put("pages", hospitalModeIPage.getPages());
        concurrentHashMap.put("current", hospitalModeIPage.getCurrent());
        concurrentHashMap.put("size", hospitalModeIPage.getSize());
        log.info(hospitalModeIPage.getRecords().toString());
        return GlobalResult.globalResult().ok(concurrentHashMap);
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    @GetMapping("/list")
    public GlobalResult getList() {
        List<HospitalMode> hospitalModeList = blogService.getList();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("list", hospitalModeList);
        return GlobalResult.globalResult().ok(hashMap);
    }

}
