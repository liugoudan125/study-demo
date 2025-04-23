package com.beiming.mongo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Person
 */
@Document(collection = "aaa")
@Setter
@Getter
@ToString
public class Person {

    @Id
    private String _id;

    @Field(name = "id")
    @Indexed(name = "idx_id", direction = IndexDirection.ASCENDING)
    private Long id;

    @Field(name = "name")
    private String name;

    @Field(name = "age")
    private Integer age;

    private List<Car> cars;

    @Field(name = "createTime")
    private LocalDateTime createTime;
}
