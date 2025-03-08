package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.entity.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/1 01:17
 * @Project: teakWeb
 * @File: FileService.java
 * @Description:
 */
public interface FileService extends IService<FileModel> {


    FileModel uploadFile(Long userId, MultipartFile file, String directory);

    void safeDelete(String objectKey, String archiveDir);
}
