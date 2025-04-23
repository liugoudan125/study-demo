package com.beiming.novel_crawler.spider.processor.filter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * BookProcessorStrategy
 */
@Component
public class BookProcessorFilter extends AbstractProcessorFilter {

    public final static Pattern BOOK_PATTERN = Pattern.compile("https://69shuba.cx/book/\\d+\\.htm");


    @Override
    protected boolean isMatch(String url) {
        return BOOK_PATTERN.matcher(url).matches();
    }

    @Override
    protected void handleData(Page page) {
        Document document = page.getHtml().getDocument();
        ResultItems resultItems = page.getResultItems();
        resultItems.put("type", "book");

        resultItems.put("siteUrl", page.getUrl().get());
        Element bookInfoElement = document.selectFirst(".booknav2");
        if (null != bookInfoElement) {
            //书名
            String name = getText(bookInfoElement.selectFirst("h1 > a"));
            resultItems.put("name", name);
            Elements pNodes = bookInfoElement.select("p");
            for (Element pNode : pNodes) {
                if (pNode.text().contains("作者：")) {
                    //作者
                    String author = getText(pNode.selectFirst("a"));
                    resultItems.put("author", author);
                } else if (pNode.text().contains("分类：")) {
                    //分类
                    String classification = getText(pNode.selectFirst("a"));
                    resultItems.put("classification", classification);
                } else if (pNode.text().contains("更新：")) {
                    String updateTime = getText(pNode).replace("更新：", "");
                    resultItems.put("updateTime", updateTime);
                } else {
                    String other = getText(pNode);
                    resultItems.put("other", other);
                }
            }
        }

        //标签
        Element tagUlEl = document.getElementById("tagul");
        if (null != tagUlEl) {
            List<String> tags = new ArrayList<>();
            Elements aTags = tagUlEl.getElementsByTag("a");
            for (Element element : aTags) {
                tags.add(getText(element));
            }
            String tagStr = String.join(",", tags);
            resultItems.put("tags", tagStr);
        }


        //简介
        Element introductionEl = document.selectFirst("#jianjie-popup .content > p");
        if (null != introductionEl) {
            //简介
            String introduction = outerHtml(introductionEl);
            resultItems.put("introduction", introduction);
        }
    }
}
