package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.model.Article;
import com.teak.blog.result.GlobalResult;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/27 00:01
 * @Project: teakWeb
 * @File: ArticleService.java
 * @Description:
 */
public interface ArticleService extends IService<Article> {
    Page<Article> getPage(int pageNum, int pageSize, int status, Long userId);
}
