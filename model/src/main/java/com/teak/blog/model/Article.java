package com.teak.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Date: 2025/2/27 00:01
 * @Project: teakWeb
 * @File: Article.java
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "article")
public class Article extends BaseModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @SnowflakeAlgorithm
    private Long id;

    /**
     * 文章分标题
     */
    private String title;

    /**
     * 文章分类Id
     */
    private Long cateId;

    @TableField(exist = false)
    private String cateName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @TableField(exist = false)
    private String userName;
}
