package com.example.annotation;

/**
 * @author
 * @date 2023/7/28 16:52
 * @desc Basic
 */
@MyNameValue(name = "这是类上")
public interface Basic {

    @MyNameValue(name = "这是方法上")
     void say();
}
