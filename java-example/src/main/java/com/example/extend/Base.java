package com.example.extend;

/**
 * Base
 */
public class Base {

    private String name;

    static {
        System.out.println("Base static block executed");
    }

    {
        System.out.println("Base instance block executed");
    }

    public Base() {
        System.out.println("Base constructor executed");
    }

    public Base(String name) {
        this.name = name;
        System.out.println("Base constructor with name executed: " + name);
    }

    public Base getBase() {
        return this;
    }
}
