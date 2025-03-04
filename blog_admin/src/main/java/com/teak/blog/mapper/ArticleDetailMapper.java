package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.model.ArticleDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 17:30
 * @Project: teakWeb
 * @File: ArticleDetailMapper.java
 * @Description:
 */
@Repository
public interface ArticleDetailMapper extends BaseMapper<ArticleDetail> {
    ArticleDetail getArtDetailByArtId(@Param("articleId") Long articleId);

}
