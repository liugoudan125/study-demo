package com.beiming.novel_crawler.spider.processor.filter;

import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;

/**
 * PrcessorStrategy
 */
public interface ProcessorFilter {


    void doFilter(Page page);

    default String getText(Element element) {
        if (null == element) {
            return "";
        } else {
            return element.text();
        }
    }

    default String outerHtml(Element element) {
        if (null == element) {
            return "";
        } else {
            return element.outerHtml();
        }
    }
}
