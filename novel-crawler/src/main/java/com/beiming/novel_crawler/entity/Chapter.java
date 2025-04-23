package com.beiming.novel_crawler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Chapter
 */
@Entity(name = "chapter")
@Setter
@Getter
@ToString
public class Chapter extends BaseEntity {

    @Column(name = "book_id", columnDefinition = "bigint comment '书ID'")
    private Long bookId;

    @Column(name = "title", columnDefinition = "text comment '章节标题'")
    private String title;

    @Column(name = "content", columnDefinition = "text comment '章节内容'")
    private String content;

    @Column(name = "publish_time", columnDefinition = "varchar(20) comment '发布时间'")
    private String publishTime;

    @Column(name = "url", columnDefinition = "varchar(255) comment '地址'", unique = true)
    private String url;


}
