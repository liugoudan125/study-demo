package com.example.annotation;

import com.example.annotation.performance.Say;

import java.util.Set;

/**
 * @author
 * @date 2023/8/4 15:14
 * @desc Student
 */
public class Student implements Say {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void say() {
        System.out.println("student");
    }
}
