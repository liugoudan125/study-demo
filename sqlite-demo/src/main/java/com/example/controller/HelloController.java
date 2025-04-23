package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lcl
 * @date 2023/12/22 13:37
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "hello,world!";
    }

}
