package com.teak.blog.controller;

import com.teak.blog.model.ArticleDetail;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ArticleDetailService;
import com.teak.blog.vo.ArticleAndDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 17:28
 * @Project: teakWeb
 * @File: ArticleDetailController.java
 * @Description:
 */
@RestController
@RequestMapping("/api/articleDetail")
@Slf4j
public class ArticleDetailController {
    private final ArticleDetailService articleDetailService;

    public ArticleDetailController(ArticleDetailService articleDetailService) {
        this.articleDetailService = articleDetailService;
    }

    @GetMapping("/getArtDetailByArtId/{articleId}")
    public GlobalResult getArtDetailByArtId(@PathVariable Long articleId) {

        HashMap<String, Object> hashMap = new HashMap<>();

        ArticleAndDetailVo articleAndDetailVo = articleDetailService.getArtDetailByArtId(articleId);
        hashMap.put("articleAndDetailVo", articleAndDetailVo);
        return new GlobalResult().ok(hashMap);
    }


}
