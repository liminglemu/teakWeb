package com.teak.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teak.blog.model.Article;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ArticleService;
import com.teak.blog.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/27 00:00
 * @Project: teakWeb
 * @File: ArticleController.java
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getArticlePage")
    public GlobalResult getPage(@RequestParam(defaultValue = "1") int pageNum,
                                @RequestParam(defaultValue = "5") int pageSize,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String category,
                                @RequestParam Long userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Page<Article> page = articleService.getPage(pageNum, pageSize, category, status, userId);
        hashMap.put("page", page);
        return new GlobalResult().ok(hashMap);
    }

    @PostMapping("/addArticle")
    public GlobalResult addArticle(@RequestBody ArticleVo articleVo) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Article article = articleService.addArticle(articleVo);
        hashMap.put("article", article);
        return new GlobalResult().ok(hashMap);
    }

    @PostMapping("/delArticle")
    public GlobalResult delArticle(@RequestBody Long id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        articleService.delArticle(id);
        hashMap.put("article", null);
        return new GlobalResult().ok(hashMap);
    }
    @PostMapping("/updateArticle")
    public GlobalResult updateArticle(@RequestBody ArticleVo articleVo) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Article article = articleService.addArticle(articleVo);
        hashMap.put("article", article);
        return new GlobalResult().ok(hashMap);
    }

}
