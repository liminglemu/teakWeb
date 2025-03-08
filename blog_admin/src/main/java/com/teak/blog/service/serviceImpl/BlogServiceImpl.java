package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.HospitalMode;
import com.teak.blog.mapper.BlogMapper;
import com.teak.blog.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Blog service.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/18
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, HospitalMode> implements BlogService {
    @Override
    public List<HospitalMode> getList() {
        return baseMapper.selectList(new QueryWrapper<HospitalMode>().eq(true, "status", 1));
    }

    @Override
    public IPage<HospitalMode> getPage(int pageNum, int pageSize, int status) {
        return baseMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<HospitalMode>().eq("status", status));
    }
}
