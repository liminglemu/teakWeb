package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.entity.model.Cart;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/21 12:53
 * @Project: teakWeb
 * @File: CartService.java
 * @Description:
 */
public interface CartService extends IService<Cart> {
    List<Cart> getList();
}
