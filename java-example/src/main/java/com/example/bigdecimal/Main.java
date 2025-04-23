package com.example.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.example.bigdecimal.BigDecimalUtil.checkBigDecimal;

/**
 * @author lcl
 * @date 2023/8/14 15:36
 * @desc Main
 */
public class Main {
    public static void main(String[] args) {
        BigDecimal bigDecimal = BigDecimal.valueOf(230);
        BigDecimal bigDecimal1 = bigDecimal.setScale(4, RoundingMode.HALF_DOWN);
        System.out.println(checkBigDecimal(null));
    }

}
