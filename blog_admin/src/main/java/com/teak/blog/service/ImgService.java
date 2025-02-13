package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.model.Img;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/13 14:32
 * @Project: teakWeb
 * @File: ImgService.java
 * @Description:
 */
public interface ImgService extends IService<Img> {
    List<Img> getImgList();
}
