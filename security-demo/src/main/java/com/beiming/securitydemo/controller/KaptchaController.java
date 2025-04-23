package com.beiming.securitydemo.controller;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * KaptchaController
 */
@RestController
//@RequestMapping("captcha")
public class KaptchaController {


//    @GetMapping("get")
    public void get(HttpServletResponse response) {
    }
}
