package com.teak.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.File;

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
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cateId;
    private String content;
    private File articleCover;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String userId;
}
