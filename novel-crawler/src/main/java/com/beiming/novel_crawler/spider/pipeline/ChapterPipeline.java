package com.beiming.novel_crawler.spider.pipeline;

import com.beiming.novel_crawler.entity.Book;
import com.beiming.novel_crawler.entity.Chapter;
import com.beiming.novel_crawler.service.BookService;
import com.beiming.novel_crawler.service.ChapterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;

/**
 * MysqlPipeline
 */
@Component
@Slf4j
public class ChapterPipeline extends AbstractPipeline {

    @Resource
    private ChapterService chapterService;

    @Resource
    private BookService bookService;

    @Override
    protected void process(ResultItems resultItems) {
        Chapter chapter = new Chapter();
        chapter.setUrl(resultItems.get("url"));
        chapter.setTitle(resultItems.get("title"));
        chapter.setPublishTime(resultItems.get("publishTime"));
        chapter.setContent(resultItems.get("content"));
        String bookUrl = resultItems.get("bookUrl");
        if (bookUrl != null && !bookUrl.trim().isBlank()) {
            Book book = bookService.getBySiteUrl(bookUrl);
            chapter.setBookId(book.getId());
        } else {
            log.info("章节对应书籍的url为空, 章节url {}", chapter.getUrl());
        }
        chapterService.save(chapter);
    }

    @Override
    protected String getType() {
        return "chapter";
    }
}
