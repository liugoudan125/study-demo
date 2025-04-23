package com.beiming.spider.config;

import com.beiming.spider.properties.SeleniumProperties;
import com.beiming.spider.selenium.SeleniumManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SeleniumConfig-2024/3/26-11:44
 */
@EnableConfigurationProperties(SeleniumProperties.class)
@Configuration
public class SeleniumConfig {

    @Bean
    public ChromeOptions chromeOptions(SeleniumProperties properties) {
        if (properties.getDriver() != null && !properties.getDriver().isBlank()) {
            System.setProperty("webdriver.chrome.driver", properties.getDriver());
        }
        ChromeOptions options = new ChromeOptions();
        if (properties.getHeadless() != null && properties.getHeadless().equals(Boolean.TRUE)) {
            options.addArguments("--headless");
        }
        if (properties.getBrowser() != null && !properties.getBrowser().isBlank()) {
            options.setBinary(properties.getBrowser());
        }
        return options;
    }

    @Bean
    public SeleniumManager seleniumManager(ChromeOptions chromeOptions) {
        return new SeleniumManager(chromeOptions);
    }

}
