package com.teak.blog;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.teak.blog.annotation.CreateTime;
import com.teak.blog.annotation.IsDeleted;
import com.teak.blog.annotation.Statue;
import com.teak.blog.annotation.UpdateTime;
import lombok.Data;

import java.util.Date;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/23 15:51
 * @Project: teakWeb
 * @File: BaseModel.java
 * @Description:
 */

@Data
public class BaseModel {

    @Statue
    protected Integer status;
    @CreateTime
    protected Date createTime;
    @UpdateTime
    protected Date updateTime;
    @TableLogic(value = "0", delval = "1")
    @IsDeleted
    protected Integer isDeleted;
}
