package com.beiming.novel_crawler.spider;

import com.beiming.novel_crawler.spider.download.SeleniumDownloader;
import com.beiming.novel_crawler.spider.processor.Book69Processor;
import com.beiming.novel_crawler.spider.processor.filter.ProcessorFilterChain;
import com.beiming.novel_crawler.spider.properties.SpiderProperties;
import com.beiming.novel_crawler.spider.properties.WebDriverProperties;
import com.beiming.novel_crawler.spider.scheduler.MysqlQueueScheduler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

import javax.management.JMException;
import java.util.List;
import java.util.UUID;

/**
 * CrawlerConfig
 */
@Configuration
@Import({WebDriverProperties.class, SpiderProperties.class})
public class CrawlerConfig {

    @Resource
    private List<Pipeline> pipelines;


    @Resource
    private WebDriverProperties webDriverProperties;

    @Bean
    public PageProcessor processor(ProcessorFilterChain filterChain) {
        return new Book69Processor(filterChain);
    }


    @Bean
    public Downloader downloader() {
        return new SeleniumDownloader(webDriverProperties);
    }

    @Bean
    public Scheduler scheduler(JdbcTemplate jdbcTemplate) {
        return new MysqlQueueScheduler(jdbcTemplate);
    }

    @Bean
    public Spider spider(PageProcessor processor, Downloader downloader, Scheduler scheduler) throws JMException {
        Spider spider = Spider.create(processor)
                .setPipelines(pipelines)
                .setScheduler(scheduler)
                .setDownloader(downloader)
                .setUUID(UUID.randomUUID().toString())
                .thread(30);
        SpiderMonitor.instance()
                .register(spider);
        return spider;
    }

}
