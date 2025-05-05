package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.Img;
import com.teak.blog.mapper.ImgDao;
import com.teak.blog.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/13 14:32
 * @Project: teakWeb
 * @File: ImgServiceImp.java
 * @Description:
 */
@Service
@Slf4j
public class ImgServiceImp extends ServiceImpl<ImgDao, Img> implements ImgService {
    private final ImgDao imgDao;

    public ImgServiceImp(ImgDao imgDao) {
        this.imgDao = imgDao;
    }

    @Override
    public List<Img> getImgList() {
        return imgDao.getImgList();
    }
}
