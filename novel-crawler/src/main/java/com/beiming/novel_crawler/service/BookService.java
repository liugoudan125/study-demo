package com.beiming.novel_crawler.service;

import com.beiming.novel_crawler.entity.Book;
import com.beiming.novel_crawler.repository.BookRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookService
 */
@Service
public class BookService {
    @Resource
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public void save(Book book) {
        Book b = bookRepository.findBySiteUrl(book.getSiteUrl());
        if (b != null) {
            book.setId(b.getId());
        } else {
            book.setId(null);
        }
        bookRepository.save(book);
    }

    public Book getBySiteUrl(String siteUrl) {
        if (siteUrl == null || siteUrl.trim().isEmpty()) {
            return null;
        }
        Book book = bookRepository.findBySiteUrl(siteUrl);
        if (book == null) {
            book = new Book();
            book.setSiteUrl(siteUrl);
            book = bookRepository.save(book);
        }
        return book;
    }
}
