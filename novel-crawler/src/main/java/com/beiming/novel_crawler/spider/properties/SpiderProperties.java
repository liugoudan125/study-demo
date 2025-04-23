package com.beiming.novel_crawler.spider.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * WebDriverProperties
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "spider")
public class SpiderProperties {

    private List<String> urls;


}
