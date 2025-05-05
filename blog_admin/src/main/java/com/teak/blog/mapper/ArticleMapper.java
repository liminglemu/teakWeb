package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.entity.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/27 00:04
 * @Project: teakWeb
 * @File: ArticleMapper.java
 * @Description:
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
}
