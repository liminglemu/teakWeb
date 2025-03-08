package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import com.teak.blog.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
@TableName(value = "xh_product")
public class XhProduct extends BaseModel {
    @SnowflakeAlgorithm
    private Long id;
    private String productName;
    private BigDecimal productPrice;
}
