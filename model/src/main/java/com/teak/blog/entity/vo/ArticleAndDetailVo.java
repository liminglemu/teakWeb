package com.teak.blog.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.teak.blog.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/28 22:36
 * @Project: teakWeb
 * @File: ArticleAndDetailVo.java
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleAndDetailVo extends BaseModel {


    /**
     * 详情的id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章分类Id
     */
    private Long cateId;

    @TableField(exist = false)
    private String cateName;


    private String content;

    private String articleCover;
}
