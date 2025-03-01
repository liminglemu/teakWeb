package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.model.ArticleDetail;
import com.teak.blog.vo.ArticleAndDetailVo;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 17:29
 * @Project: teakWeb
 * @File: ArticleDetailService.java
 * @Description:
 */
public interface ArticleDetailService extends IService<ArticleDetail> {
    ArticleAndDetailVo getArtDetailByArtId(Long articleId);

}
