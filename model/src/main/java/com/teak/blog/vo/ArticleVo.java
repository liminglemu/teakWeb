package com.teak.blog.vo;

import com.teak.blog.model.Category;
import lombok.Data;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/26 14:31
 * @Project: teakWeb
 * @File: ArticleVo.java
 * @Description:
 */
@Data
public class ArticleVo {
    private Category article;
    private Long userId;
}
