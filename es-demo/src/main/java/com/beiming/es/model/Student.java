package com.beiming.es.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * Student
 */
public class Student {

    @Id
    @Field(name = "id", type = FieldType.Keyword)
    private String id;

    @Field(name = "name", type = FieldType.Text)
    private String name;


    @Field(name = "tags", type = FieldType.Text)
    private List<String> tags;

    @Field(name = "description", type = FieldType.Text)
    private String description;

    @Field(name = "create_time",type = FieldType.Date)
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
