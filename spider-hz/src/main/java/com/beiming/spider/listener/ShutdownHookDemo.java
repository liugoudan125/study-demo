package com.beiming.spider.listener;

import com.beiming.spider.selenium.SeleniumManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShutdownHookDemo implements ApplicationListener<ContextClosedEvent> {
    @Resource
    private SeleniumManager seleniumManager;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        seleniumManager.shutdown();
    }
}
