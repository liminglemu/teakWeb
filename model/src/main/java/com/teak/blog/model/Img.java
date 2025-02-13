package com.teak.blog.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 图片
 * img
 */
@Data
public class Img implements Serializable {
    private Long id;

    /**
     * 图片名称
     */
    private String imgName;

    private static final long serialVersionUID = 1L;
}