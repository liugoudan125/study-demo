package com.beiming.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 */
@RestController
@RequestMapping("guest")
public class GuestController {

    @GetMapping("resource1")
    public String resource1() {
        return "admin/resource1";
    }

    @GetMapping("resource2")
    public String resource2() {
        return "admin/resource2";
    }

    @GetMapping("resource3")
    public String resource3() {
        return "admin/resource3";
    }

    @GetMapping("resource4")
    public String resource4() {
        return "admin/resource4";
    }

}
