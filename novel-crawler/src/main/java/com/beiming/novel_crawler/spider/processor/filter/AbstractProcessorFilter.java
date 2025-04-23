package com.beiming.novel_crawler.spider.processor.filter;

import us.codecraft.webmagic.Page;

/**
 * AbstractProcessorFilter
 */
public abstract class AbstractProcessorFilter implements ProcessorFilter {

    @Override
    public void doFilter(Page page) {
        if (isMatch(page.getUrl().get())) {
            handleData(page);
        }
    }

    protected abstract boolean isMatch(String url);

    protected abstract void handleData(Page page);
}
