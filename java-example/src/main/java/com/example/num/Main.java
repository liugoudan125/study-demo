package com.example.num;

import java.math.BigInteger;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // 计算 1! 到 10000! 的求和
        BigInteger sum = BigInteger.ZERO;
        BigInteger factorial = BigInteger.ONE;

        for (int i = 1; i <= 10000; i++) {
            // 计算 i! = (i-1)! * i
            factorial = factorial.multiply(BigInteger.valueOf(i));
            // 累加到总和
            sum = sum.add(factorial);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("计算耗时：" + (endTime - startTime) + " 毫秒");
//        System.out.println("\n计算完成！");
//        System.out.println("1! + 2! + 3! + ... + 10000! 的结果：");
//        System.out.println("结果的位数：" + sum.toString().length() + " 位");

        // 显示结果的前100位和后100位
        String sumStr = sum.toString();
//        if (sumStr.length() > 200) {
//            System.out.println("\n结果的前100位：" + sumStr.substring(0, 100));
            System.out.println("结果的后100位：" + sumStr.substring(sumStr.length() - 100));
//        } else {
            System.out.println("\n完整结果：" + sumStr);
//        }
    }

}