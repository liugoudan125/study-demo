package com.beiming.novel_crawler.spider.pipeline;

import com.beiming.novel_crawler.entity.Book;
import com.beiming.novel_crawler.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;

/**
 * MysqlPipeline
 */
@Component
public class BookPipeline extends AbstractPipeline {

    @Resource
    private BookService bookService;

    @Override
    protected void process(ResultItems resultItems) {
        Book book = new Book();
        book.setAuthor(resultItems.get("author"));
        book.setClassification(resultItems.get("classification"));
        book.setName(resultItems.get("name"));
        book.setOther(resultItems.get("other"));
        book.setTags(resultItems.get("tags"));
        book.setUpdateTime(resultItems.get("updateTime"));
        book.setIntroduction(resultItems.get("introduction"));
        book.setSiteUrl(resultItems.get("siteUrl"));
        bookService.save(book);
    }

    @Override
    protected String getType() {
        return "book";
    }


}
