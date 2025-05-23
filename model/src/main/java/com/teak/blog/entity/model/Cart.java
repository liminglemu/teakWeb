package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/21 12:49
 * @Project: teakWeb
 * @File: Cart.java
 * @Description:
 */
@TableName(value = "cart")
@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    @SnowflakeAlgorithm
    private Long id;
    private String name;
    private BigDecimal price;
    private Long count;
    private String thumb;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", count=" + count +
                ", thumb=" + thumb +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}
