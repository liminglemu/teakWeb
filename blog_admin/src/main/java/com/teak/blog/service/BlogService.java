package com.teak.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.model.HospitalMode;

import java.util.List;

/**
 * The interface Blog service.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/18
 */
public interface BlogService extends IService<HospitalMode> {
    /**
     * Gets list.
     *
     * @return the list
     */
    List<HospitalMode> getList();

    /**
     * Gets page.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @param status   the status
     * @return the page
     */
    IPage<HospitalMode> getPage(int pageNum, int pageSize, int status);
}
