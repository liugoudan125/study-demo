package com.beiming.novel_crawler.spider.processor.filter;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Selectable;

/**
 * DefaultProcessorStrategy
 * 提取url,添加到targetRequest
 */
@Component
public class DefaultProcessorFilter extends AbstractProcessorFilter {

    @Override
    protected boolean isMatch(String url) {
        return true;
    }

    @Override
    protected void handleData(Page page) {
        Selectable links = page.getHtml().links();
        for (Selectable node : links.nodes()) {
            String requestUrl = node.get();
            if (requestUrl.startsWith("https://69shuba.cx")) {
                if (!requestUrl.contains("jumpurl=")) {
                    Request request = new Request(requestUrl);
                    request.putExtra("status", "READY");
                    page.addTargetRequest(request);
                }
            }
        }
    }
}
