package com.teak.blog.entity.vo;

import com.teak.blog.entity.model.Category;
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
    private Long userId;
}
