package com.beiming.novel_crawler.repository;

import com.beiming.novel_crawler.entity.Book;
import com.beiming.novel_crawler.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BookRepository
 */
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Chapter findByUrl(String url);

}
