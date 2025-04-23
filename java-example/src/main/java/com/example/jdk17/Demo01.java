package com.example.jdk17;


import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;

/**
 * @author lcl
 * @date 2023/8/29 17:40
 * @desc Demo01
 */
public class Demo01 {
    public static void main(String[] args) {
        var object = new HashMap<String, Object>();
        object.put("aaa", 123);
        System.out.println(object);
        //string template
        com.example.jdk17.Base base = new com.example.jdk17.BaseImpl();
        if (base instanceof com.example.jdk17.BaseImpl base1) {
            base1.selfSay();
        }

        //record
        Sss sss = new com.example.jdk17.Sss(1, "aa", LocalDate.of(2019, 12, 11));
        System.out.println(sss);
        //string
        String s = """
                jfoajfoa
                jfoajogja
                jgoasjgojqohenallga
                jgoahgoajg
                """;
        System.out.println(s);


        LocalDate now = LocalDate.now();
        System.out.println(YearMonth.now().lengthOfMonth());
    }
}
