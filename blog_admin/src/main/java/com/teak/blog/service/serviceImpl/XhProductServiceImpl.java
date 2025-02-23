package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.mapper.XhProductMapper;
import com.teak.blog.model.XhProduct;
import com.teak.blog.service.XhProductService;
import org.springframework.stereotype.Service;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:40
 * @Project: teakWeb
 * @File: XhProductServiceImpl.java
 * @Description:
 */
@Service
public class XhProductServiceImpl extends ServiceImpl<XhProductMapper, XhProduct> implements XhProductService {
    private final XhProductMapper xhProductMapper;

    public XhProductServiceImpl(XhProductMapper xhProductMapper) {
        this.xhProductMapper = xhProductMapper;
    }
}
