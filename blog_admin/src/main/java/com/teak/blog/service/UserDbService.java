package com.teak.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teak.blog.model.UserDb;
import com.teak.blog.vo.RegisterVo;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:40
 * @Project: teakWeb
 * @File: XhProductService.java
 * @Description:
 */
public interface UserDbService extends IService<UserDb> {
    List<UserDb> finListByName(UserDb userDb);

    UserDb finByNameAndPswd(RegisterVo registerVo);

    UserDb getByToken(String token);
}
