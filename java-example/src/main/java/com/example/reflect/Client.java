package com.example.reflect;

import java.lang.reflect.Field;

/**
 * Client
 */
public class Client {
    public static void main(String[] args) throws NoSuchFieldException {
        Class<Person> personClass = Person.class;
        // 获取当前类声明的所有字段（包括私有、受保护和默认访问权限的字段），不包括父类的字段。
        System.out.println(personClass.getDeclaredField("name"));
        //获取当前类或其父类中声明的公共字段（仅 public 修饰的字段）。
        System.out.println(personClass.getField("name"));

    }

}
