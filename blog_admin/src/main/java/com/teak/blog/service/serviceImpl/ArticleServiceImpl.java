package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.mapper.ArticleMapper;
import com.teak.blog.model.Article;
import com.teak.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/27 00:03
 * @Project: teakWeb
 * @File: ArticleServiceImpl.java
 * @Description:
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public Page<Article> getPage(int pageNum, int pageSize, int status, Long userId) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status == 0) {
            wrapper.eq("status", 0);
        } else if (status == 1) {
            wrapper.eq("status", 1);
        }
        return articleMapper.selectPage(page, wrapper);
    }
}
