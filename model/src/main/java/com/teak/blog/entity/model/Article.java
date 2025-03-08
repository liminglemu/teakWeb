package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import com.teak.blog.entity.BaseModel;
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

    private Long userId;

    @TableField(exist = false)
    private String userName;
}
