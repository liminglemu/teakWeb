package com.teak.blog.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.teak.blog.BaseModel;
import com.teak.blog.annotation.SnowflakeAlgorithm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 17:26
 * @Project: teakWeb
 * @File: ArticleDetail.java
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "article_detail")
public class ArticleDetail extends BaseModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @SnowflakeAlgorithm
    private Long id;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long articleId;
    private String articleCover;

}
