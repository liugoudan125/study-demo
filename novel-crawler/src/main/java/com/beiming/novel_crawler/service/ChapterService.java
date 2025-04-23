package com.beiming.novel_crawler.service;

import com.beiming.novel_crawler.entity.Chapter;
import com.beiming.novel_crawler.repository.ChapterRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * ChapterService
 */
@Service
public class ChapterService {

    @Resource
    private ChapterRepository chapterRepository;

    public void save(Chapter chapter) {

        Chapter c = chapterRepository.findByUrl(chapter.getUrl());
        if (c != null) {
            chapter.setId(c.getId());
        } else {
            chapter.setId(null);
        }
        chapterRepository.save(chapter);
    }
}
