package com.beiming.spider.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SeleniumProperties-2024/3/26-11:40
 */
@ConfigurationProperties(prefix = "selenium")
@Data
public class SeleniumProperties {

    private String driver;

    private String browser;

    private Boolean headless;

}
