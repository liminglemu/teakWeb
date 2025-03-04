package com.teak.blog.controller;

import com.teak.blog.model.Img;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/13 14:36
 * @Project: teakWeb
 * @File: ImgController.java
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api/img")
public class ImgController {
    private final ImgService imgService;

    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @GetMapping("/imgList")
    public GlobalResult getImgList() {
        List<Img> ImgList = imgService.getImgList();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ImgList", ImgList);
        return GlobalResult.success(hashMap);
    }
}
