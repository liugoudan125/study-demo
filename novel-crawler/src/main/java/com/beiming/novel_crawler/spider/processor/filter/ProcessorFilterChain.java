package com.beiming.novel_crawler.spider.processor.filter;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * ProcessorStrategyFactory
 */
@Component
public class ProcessorFilterChain {

    @Resource
    private List<ProcessorFilter> filters;

    public void filter(Page page) {
        for (ProcessorFilter filter : filters) {
            filter.doFilter(page);
        }
    }
}
