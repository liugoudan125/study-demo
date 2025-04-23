package com.beiming.novel_crawler.controller;

import com.beiming.novel_crawler.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiming.novel_crawler.entity.Book;

import java.util.List;


/**
 * BookController
 */
@RestController
@RequestMapping("book")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("list")
    public List<Book> list() {
        return bookService.list();
    }

    @PostMapping("add")
    public Boolean add(@RequestBody Book book) {
        bookService.save(book);
        return true;
    }
}
