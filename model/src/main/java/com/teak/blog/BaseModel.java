package com.teak.blog;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    @TableField(fill = FieldFill.INSERT)
    protected Integer status;

    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;

    @TableLogic(value = "0", delval = "1")
    @TableField(fill = FieldFill.INSERT)
    protected Integer isDeleted;
}
