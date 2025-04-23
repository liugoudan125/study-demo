package com.beiming.novel_crawler.spider.processor;

import com.beiming.novel_crawler.spider.processor.filter.ProcessorFilterChain;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Book69Processor
 * 解析 69书城页面
 */
public class Book69Processor implements PageProcessor {

    private final Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0")
            .setRetryTimes(3)
            .setCycleRetryTimes(5)
            .setSleepTime(1000);

    private final ProcessorFilterChain processorFilterChain;

    public Book69Processor(ProcessorFilterChain processorFilterChain) {
        this.processorFilterChain = processorFilterChain;
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        processorFilterChain.filter(page);
    }

}
