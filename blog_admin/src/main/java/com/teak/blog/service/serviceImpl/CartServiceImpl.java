package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.Cart;
import com.teak.blog.mapper.CartMapper;
import com.teak.blog.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/21 12:53
 * @Project: teakWeb
 * @File: CartServiceImpl.java
 * @Description:
 */
@Slf4j
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    private final CartMapper cartMapper;

    public CartServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }


    @Override
    public List<Cart> getList() {
        return cartMapper.selectList(null);
    }
}
