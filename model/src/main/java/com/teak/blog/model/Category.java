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
 * @Date: 2025/2/15 15:34
 * @Project: teakWeb
 * @File: XhProduct.java
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "category")
public class Category extends BaseModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @SnowflakeAlgorithm
    private Long id;

    /**
     * 文章分类名称
     */
    private String cateName;

    /**
     * 文章分类别名
     */
    private String cateAlias;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

}
