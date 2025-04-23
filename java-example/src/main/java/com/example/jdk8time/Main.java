package com.example.jdk8time;

import java.time.LocalDate;

/**
 * @author lcl
 * @date 2023/8/15 9:27
 * @desc Main
 */
public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusMonths(3).minusDays(today.getDayOfMonth() - 1);
        LocalDate maxDate = today.minusDays(4);
        System.out.println(minDate);
        System.out.println(maxDate);
    }
}
