package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.ArticleDetail;
import com.teak.blog.mapper.ArticleDetailMapper;
import com.teak.blog.service.ArticleDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 17:29
 * @Project: teakWeb
 * @File: ArticleDetailServiceImpl.java
 * @Description:
 */
@Service
@Slf4j
public class ArticleDetailServiceImpl extends ServiceImpl<ArticleDetailMapper, ArticleDetail> implements ArticleDetailService {
    private final ArticleDetailMapper articleDetailMapper;

    public ArticleDetailServiceImpl(ArticleDetailMapper articleDetailMapper) {
        this.articleDetailMapper = articleDetailMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetail getArtDetailByArtId(Long articleId) {
        return articleDetailMapper.getArtDetailByArtId(articleId);
    }

}
