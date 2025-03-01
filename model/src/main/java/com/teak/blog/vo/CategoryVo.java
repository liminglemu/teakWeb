package com.teak.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teak.blog.model.Category;
import lombok.Data;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/26 14:31
 * @Project: teakWeb
 * @File: CategoryVo.java
 * @Description:
 */
@Data
public class CategoryVo {
    private Category article;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
}
