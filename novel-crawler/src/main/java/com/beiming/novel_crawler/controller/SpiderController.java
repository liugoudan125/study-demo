package com.beiming.novel_crawler.controller;

import com.beiming.novel_crawler.spider.properties.SpiderProperties;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SpiderController
 */
@RestController
@RequestMapping("spider")
public class SpiderController {

    @Resource
    private Spider spider;

    @Resource
    private SpiderProperties spiderProperties;


    private final AtomicBoolean running = new AtomicBoolean(false);

    @PostMapping("start")
    public String start() {
        Thread.ofVirtual().start(() -> {
            if (running.compareAndSet(false, true)) {
                spider.startUrls(spiderProperties.getUrls());
                spider.run();
                running.compareAndSet(true, false);
            }
        });
        return "start";
    }

    @PostMapping("stop")
    public String stop() {
        if (running.compareAndSet(true, false)) {
            spider.stop();
        }
        return "stop";
    }

    @GetMapping("monitor")
    public SpiderListener monitor() {
        for (SpiderListener spiderListener : spider.getSpiderListeners()) {
            if (spiderListener instanceof SpiderMonitor.MonitorSpiderListener monitorSpiderListener) {
                return monitorSpiderListener;
            }
        }
        return null;
    }
}
