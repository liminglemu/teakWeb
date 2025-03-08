package com.teak.blog.controller;

import com.teak.blog.entity.model.UserDb;
import com.teak.blog.entity.vo.RegisterVo;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.UserDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/15 15:33
 * @Project: teakWeb
 * @File: XhProductController.java
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api/userDb")
public class UserDbController {
    private final UserDbService userDbService;

    public UserDbController(UserDbService userDbService) {
        this.userDbService = userDbService;
    }


    @PostMapping("/register")
    public GlobalResult register(@RequestBody RegisterVo registerVo) {
        UserDb userDb = new UserDb();
        userDb.setUserName(registerVo.getUserName());
        userDb.setPassword(registerVo.getPassword());
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            List<UserDb> userDbList = userDbService.finListByName(userDb);
            if (userDbList.isEmpty()) {
                userDbService.save(userDb);
                hashMap.put("userDb", userDb);
                return GlobalResult.success(hashMap);
            } else {
                return GlobalResult.errorWithMessage(null, "用户名已存在");
            }
        } catch (Exception e) {
            return GlobalResult.errorWithMessage(null, e.getMessage());
        }
    }

    @PostMapping("/login")
    public GlobalResult login(@RequestBody RegisterVo registerVo) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            UserDb userDb = userDbService.finByNameAndPswd(registerVo);
            if (userDb == null) {
                return GlobalResult.errorWithMessage(null, "用户名不存在或者密码错误");
            } else {
                hashMap.put("userDb", userDb);
                return GlobalResult.success(hashMap);
            }
        } catch (Exception e) {
            return GlobalResult.errorWithMessage(null, e.getMessage());
        }
    }

    @PostMapping("/getByToken")
    public GlobalResult getByToken(@RequestBody String token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            UserDb userDb = userDbService.getByToken(token);
            if (userDb == null) {
                return GlobalResult.errorWithMessage(null, "用户不存在");
            } else {
                hashMap.put("userDb", userDb);
                return GlobalResult.success(hashMap);
            }
        } catch (Exception e) {
            return GlobalResult.errorWithMessage(null, e.getMessage());
        }
    }

}
