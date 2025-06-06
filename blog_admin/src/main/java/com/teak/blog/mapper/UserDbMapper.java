package com.teak.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teak.blog.entity.model.UserDb;
import com.teak.blog.entity.vo.RegisterVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:41
 * @Project: teakWeb
 * @File: XhProductMapper.java
 * @Description:
 */
@Repository
public interface UserDbMapper extends BaseMapper<UserDb> {
    List<UserDb> finListByName(String userName);

    UserDb finByNameAndPswd(RegisterVo registerVo);

    UserDb getByToken(String token);
}
