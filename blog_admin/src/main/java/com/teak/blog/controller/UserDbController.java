package com.teak.blog.controller;

import com.teak.blog.service.UserDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
