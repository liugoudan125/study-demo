package com.beiming.securitydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @PreAuthorize("hasRole('admin:ro')")
    @GetMapping("resource1")
    public String resource1() {
        return "admin/resource1";
    }

    @PreAuthorize("hasRole('admin:ro')")
    @GetMapping("resource2")
    public String resource2() {
        return "admin/resource2";
    }

    @PreAuthorize("hasRole('admin:rw')")
    @GetMapping("resource3")
    public String resource3() {
        return "admin/resource3";
    }

    @PreAuthorize("hasRole('admin:check')")
    @GetMapping("resource4")
    public String resource4() {
        return "admin/resource4";
    }

}
