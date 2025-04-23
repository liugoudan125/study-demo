package com.example.safe;

import net.sf.jsqlparser.expression.WhenClause;

/**
 * @author lcl
 * @date 2023/8/16 16:45
 * @desc Demo
 */
public class Demo {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                while (true) {

                    String str1 = "ABCDEFGHIJKLMN";
                    String str2 = "123456788990";
                    System.out.println(str2);
                    System.out.println(str1);
                }
            }).start();
        }
    }

}
