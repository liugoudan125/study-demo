package com.beiming.es.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * Hit
 */
@Getter
@Setter
//@Document(indexName = "lcl_test_index", dynamic = Dynamic.TRUE)
public class Hit {

    @Id
    @Field(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Field(name = "content")
    private String content;

    @Field(name = "create_at")
    private Date createAt;
}
