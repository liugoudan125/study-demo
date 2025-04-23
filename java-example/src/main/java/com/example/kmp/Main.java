package com.example.kmp;

import java.util.Arrays;

/**
 * @author lcl
 * @date 2023/11/16 10:47
 * @desc Main
 */
public class Main {
    public static void main(String[] args) {
        String pattern = "ABABCABAB";
        int[] pmt = computePMT(pattern);
        String text = "ABABDABACDABABCABAB";
        System.out.println(Arrays.toString(pmt));
        System.out.println(Arrays.toString(computePMT(text)));
        System.out.println(kmpSearch(text, pattern));

    }

    public static int kmpSearch(String text, String pattern) {
        int m = text.length();
        int n = pattern.length();
        int[] pmt = computePMT(pattern);

        int i = 0; // 指向文本字符串的指针
        int j = 0; // 指向模式字符串的指针

        while (i < m) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
                if (j == n) {
                    return i - j; // 匹配成功，返回匹配的起始位置
                }
            } else {
                if (j != 0) {
                    j = pmt[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1; // 未找到匹配的子串
    }

    private static int[] computePMT(String pattern) {
        int n = pattern.length();
        int[] pmt = new int[n];
        int j = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = pmt[j - 1];
            }

            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            pmt[i] = j;
        }

        return pmt;
    }

}
