package com.teak.blog.entity.vo;

import lombok.Data;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 17:16
 * @Project: teakWeb
 * @File: ArticleVo.java
 * @Description:
 */
@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String categoryName;
    private String content;
    private String articleCoverUrl;
    private String status;
    private Long userId;
}
