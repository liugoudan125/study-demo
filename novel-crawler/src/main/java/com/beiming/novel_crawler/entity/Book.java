package com.beiming.novel_crawler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Book
 */
@Entity(name = "book")
@Getter
@Setter
@ToString
public class Book extends BaseEntity {

    @Column(name = "name", columnDefinition = "varchar(255) comment '小说名称'")
    private String name;


    @Column(name = "author", columnDefinition = "varchar(255) comment '作者'")
    private String author;

    @Column(name = "site_url", columnDefinition = "varchar(255) not null comment '小说网址'", unique = true)
    private String siteUrl;

    @Column(name = "introduction", columnDefinition = "text comment '简介'")
    private String introduction;

    @Column(name = "classification", columnDefinition = "varchar(10) comment '分类'")
    private String classification;

    @Column(name = "update_time", columnDefinition = "char(10) comment '更新'")
    private String updateTime;

    @Column(name = "tags", columnDefinition = "varchar(255) comment '标签'")
    private String tags;

    @Column(name = "other", columnDefinition = "varchar(255) comment '更新'")
    private String other;

}
