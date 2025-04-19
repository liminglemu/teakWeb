package com.teak.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teak.blog.entity.model.Article;
import com.teak.blog.entity.vo.ArticleVo;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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

        /*这里对当前线程里的数据进行获取*/
        String threadData = (String) RequestContextHolder.currentRequestAttributes().getAttribute("THREAD_DATA", RequestAttributes.SCOPE_REQUEST);
        log.info("threadData:{}", threadData);

        HashMap<String, Object> hashMap = new HashMap<>();
        Page<Article> page = articleService.getPage(pageNum, pageSize, category, status, userId);
        hashMap.put("page", page);
        return GlobalResult.success(hashMap);
    }

    @PostMapping("/addArticle")
    public GlobalResult addArticle(@RequestBody ArticleVo articleVo) {
        Article article = articleService.addArticle(articleVo);
        return GlobalResult.success(article);
    }

    @PostMapping("/delArticle")
    public GlobalResult delArticle(@RequestBody Long id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        articleService.delArticle(id);
        hashMap.put("article", null);
        return GlobalResult.success(hashMap);
    }

    @PostMapping("/updateArticle")
    public GlobalResult updateArticle(@RequestBody ArticleVo articleVo) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Article article = articleService.addArticle(articleVo);
        hashMap.put("article", article);
        return GlobalResult.success(hashMap);
    }

}
