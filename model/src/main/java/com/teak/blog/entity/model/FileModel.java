package com.teak.blog.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import com.teak.blog.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/1 01:18
 * @Project: teakWeb
 * @File: FileModel.java
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "file_url")
public class FileModel extends BaseModel {
    @SnowflakeAlgorithm
    private Long id;
    private String title;
    private String fileFormat;
    private String fileUrl;
    private Long userId;

}
