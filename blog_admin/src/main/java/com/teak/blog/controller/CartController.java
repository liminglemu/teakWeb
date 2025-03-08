package com.teak.blog.controller;

import com.teak.blog.entity.model.Cart;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/21 12:52
 * @Project: teakWeb
 * @File: CartController.java
 * @Description:
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/list")
    public GlobalResult getList() {
        List<Cart> carts = cartService.getList();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("carts", carts);
        return GlobalResult.successWithMessage(hashMap, "获取购物车列表成功");
    }
}
