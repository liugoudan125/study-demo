package com.example.equals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lcl
 * @date 2023/9/21 13:42
 * @desc Demo
 */
public class Demo {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("YYYY-MM-dd");

        Date date = dateFormat1.parse("2024-12-31");
        System.out.println(dateFormat2.format(date));

        Integer c = Integer.valueOf(127);

    }
}
