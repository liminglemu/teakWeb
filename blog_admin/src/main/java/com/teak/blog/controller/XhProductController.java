package com.teak.blog.controller;

import com.teak.blog.handler.GlobalExceptionHandler;
import com.teak.blog.model.XhProduct;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.XhProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:33
 * @Project: teakWeb
 * @File: XhProductController.java
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api/xhProduct")
public class XhProductController {
    private final XhProductService xhProductService;

    public XhProductController(XhProductService xhProductService) {
        this.xhProductService = xhProductService;
    }

    @GetMapping("/list")
    public GlobalResult getList() {
        log.info("获取商品列表");
        List<XhProduct> list = xhProductService.list();
        HashMap<String, Object> XhProductHashMap = new HashMap<>();
        XhProductHashMap.put("list", list);
        return new GlobalResult().ok(XhProductHashMap);
    }

    @PostMapping("/addProduct")
    public GlobalResult add(@RequestBody XhProduct xhProduct) {
        log.info("添加商品");
        log.info(xhProduct.toString());
        boolean flag = xhProductService.save(xhProduct);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("flag", flag);
        if (flag) {
            return new GlobalResult().ok(hashMap);
        } else {
            return new GlobalResult().fail(hashMap);
        }
    }

    @PostMapping("/deleteById")
    public GlobalResult deleteById(@RequestBody Long id) {
        log.info("删除商品", id);
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Boolean flag = xhProductService.removeById(id);
            hashMap.put("flag", flag);
            return new GlobalResult().ok(hashMap);
        } catch (Exception e) {
            return new GlobalExceptionHandler().handleException(e);
        }
    }
}
