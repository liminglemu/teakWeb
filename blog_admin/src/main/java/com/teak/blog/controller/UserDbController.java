package com.teak.blog.controller;

import com.teak.blog.model.UserDb;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.UserDbService;
import com.teak.blog.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
            userDbService.save(userDb);
            hashMap.put("userDb", userDb);
        } catch (Exception e) {
            return GlobalResult.globalResult().customMessage(null, e.getMessage());
        }
        return GlobalResult.globalResult().ok(hashMap);
    }

}
