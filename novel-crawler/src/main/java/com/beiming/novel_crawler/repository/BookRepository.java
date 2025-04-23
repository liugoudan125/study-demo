package com.beiming.novel_crawler.repository;

import com.beiming.novel_crawler.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BookRepository
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBySiteUrl(String siteUrl);

}
