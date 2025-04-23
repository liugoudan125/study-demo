package com.beiming.properties.controller;

import com.beiming.properties.properties.WhitelistProperties;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PropertiesController
 */
@RestController
@RequestMapping("properties")
public class PropertiesController {


    @Resource
    private WhitelistProperties properties;

    @GetMapping("get")
    public WhitelistProperties get() {
        return properties;
    }
}
