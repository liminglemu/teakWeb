package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import com.teak.blog.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@ToString
@Data
@TableName(value = "article_detail")
public class ArticleDetail extends BaseModel implements Serializable {
    @TableField(fill = FieldFill.INSERT)
    @SnowflakeAlgorithm
    private Long id;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    @SnowflakeAlgorithm
    private Long articleId;

    @TableField(exist = false)
    private String articleName;

    private String articleCoverUrl;

    @TableField(fill = FieldFill.INSERT)
    @SnowflakeAlgorithm
    private Long fileId;

    @TableField(exist = false)
    private String articleStatus;

    @TableField(exist = false)
    private String cateName;

}
