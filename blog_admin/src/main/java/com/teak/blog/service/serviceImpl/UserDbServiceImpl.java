package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.UserDb;
import com.teak.blog.entity.vo.RegisterVo;
import com.teak.blog.mapper.UserDbMapper;
import com.teak.blog.service.UserDbService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:40
 * @Project: teakWeb
 * @File: XhProductServiceImpl.java
 * @Description:
 */
@Service
public class UserDbServiceImpl extends ServiceImpl<UserDbMapper, UserDb> implements UserDbService {
    private final UserDbMapper userDbMapper;

    public UserDbServiceImpl(UserDbMapper userDbMapper) {
        this.userDbMapper = userDbMapper;
    }

    @Override
    public List<UserDb> finListByName(UserDb userDb) {
        return userDbMapper.finListByName(userDb.getUserName());
    }

    @Override
    public UserDb finByNameAndPswd(RegisterVo registerVo) {
        return userDbMapper.finByNameAndPswd(registerVo);
    }

    @Override
    public UserDb getByToken(String token) {
        return userDbMapper.getByToken(token);
    }
}
