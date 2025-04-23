package com.beiming.novel_crawler.spider.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WebDriverProperties
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "spider.webdriver")
public class WebDriverProperties {

    private String type;

    private String driver;

    private String binary;

    private int capacity = 5;

    private String proxy;

    private boolean noHead = false;

}
