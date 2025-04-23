package com.beiming.novel_crawler.spider.processor.filter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.regex.Pattern;

/**
 * ChapterProcessorStrategy
 */
@Component
public class ChapterProcessorFilter extends AbstractProcessorFilter {
    public final static Pattern CHAPTER_PATTERN = Pattern.compile("https://69shuba.cx/txt/\\d+/\\d+");


    @Override
    protected boolean isMatch(String url) {
        return CHAPTER_PATTERN.matcher(url).matches();
    }

    @Override
    protected void handleData(Page page) {
        Document document = page.getHtml().getDocument();
        page.putField("type", "chapter");
        //地址
        String url = page.getUrl().get();
        page.putField("url", url);

        //标题
        Element titleEl = document.selectFirst("h1.hide720");
        String title = getText(titleEl);
        page.putField("title", title);

        //发布时间
        Element pushTimeEl = document.selectFirst(".txtinfo.hide720 > span");
        String publishTime = getText(pushTimeEl);
        page.putField("publishTime", publishTime);

        //内容
        Elements contentEls = document.select(".txtnav > p");
        StringBuilder stringBuilder = new StringBuilder();
        for (Element contentEl : contentEls) {
            stringBuilder.append("\n\t");
            stringBuilder.append(getText(contentEl));
        }
        page.putField("content", stringBuilder.toString());

        Element breadEl = document.selectFirst("h3.mytitle.hide720 > .bread");
        if (breadEl != null) {
            Elements aEls = breadEl.getElementsByTag("a");
            for (Element aEl : aEls) {
                String bookUrl = aEl.attr("href");
                if (!bookUrl.isBlank()) {
                    if (BookProcessorFilter.BOOK_PATTERN.matcher(bookUrl).matches()) {
                        page.putField("bookUrl", bookUrl);
                    }
                }
            }
        }
    }
}
