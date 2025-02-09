package com.teak.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Hospital mode.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/17
 */
@Data
@TableName(value = "Hospital_mode")
@AllArgsConstructor
@NoArgsConstructor
public class HospitalMode {

    @TableId(value = "id")
    private Integer id;

    private String hosName;
    private String hosCode;
    private String apiUrl;
    private String contactsName;
    private String contactsPhone;
    private String status;
    private String createTime;
    private String updateTime;
    @TableLogic(value = "0", delval = "1")
    private String isDeleted;

}
