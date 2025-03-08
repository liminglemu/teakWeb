package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.entity.model.Img;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgDao extends BaseMapper<Img> {

    Img selectByPrimaryKey(Long id);

    List<Img> getImgList();
}