package com.teak.blog.entity.model;

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
 * @Date: 2025/2/15 15:34
 * @Project: teakWeb
 * @File: XhProduct.java
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "category")
public class Category extends BaseModel implements Serializable {
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

    private Long userId;

}
