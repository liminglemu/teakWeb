package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.entity.model.Article;
import com.teak.blog.entity.vo.ArticleVo;

import java.util.List;

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

    Page<Article> getPage(int pageNum, int pageSize, String category, String status, Long userId);

    Article addArticle(ArticleVo articleVo);

    void delArticle(Long id);

    List<Article> getAll();
}
