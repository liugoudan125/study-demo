package com.beiming.securitydemo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 */
@RestController
public class UserController {

    @GetMapping("/user/info")
    public Object info() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }

    @GetMapping("/login/fail")
    public String loginFail() {
        return "登录失败";
    }
}
