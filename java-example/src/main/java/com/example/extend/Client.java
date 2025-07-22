package com.example.extend;

/**
 * Client
 */
public class Client {
    public static void main(String[] args) {
        Sub sub = new Sub();
        Base base = sub.getBase();
        System.out.println(base);
    }
}
