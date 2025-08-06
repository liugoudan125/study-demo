package com.beiming.properties.controller;

import com.beiming.properties.properties.WhitelistProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PropertiesController
 */
@RestController
@RequestMapping("properties")
public class PropertiesController {


    @Resource
    private WhitelistProperties properties;

    @Value("{test.list:1,2,3,4}")
    private List<String> list;

    @GetMapping("get")
    public WhitelistProperties get() {
        return properties;
    }


    @GetMapping("list")
    public List<String> getList() {
        return list;
    }
}
