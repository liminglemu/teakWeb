package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import lombok.Data;

import java.io.Serializable;

/**
 * 图片
 * img
 */
@Data
@TableName(value = "img")
public class Img implements Serializable {
    private static final long serialVersionUID = 1L;
    @SnowflakeAlgorithm
    private Long id;
    private String imgName;
}