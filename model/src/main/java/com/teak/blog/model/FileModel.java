package com.teak.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teak.blog.BaseModel;
import com.teak.blog.annotation.SnowflakeAlgorithm;
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
public class FileModel extends BaseModel {
    @SnowflakeAlgorithm
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String title;
    private String fileFormat;
    private String fileUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

}
