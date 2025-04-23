package com.example.regular;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Pattern PHONE_REGEX = Pattern.compile("^1[3-9]\\d{9}$");
        List<String> phones = new ArrayList<String>(100000);
        List<Boolean> results = new ArrayList<Boolean>(100000);
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            StringBuilder stringBuilder = new StringBuilder("1");
            for (int j = 0; j < 10; j++) {
                stringBuilder.append(random.nextInt(10));
            }
            phones.add(stringBuilder.toString());
        }
        long s = System.currentTimeMillis();
        for (String phone : phones) {
            boolean matches = PHONE_REGEX.matcher(phone).matches();
            results.add(matches);
        }
        System.out.println(System.currentTimeMillis() - s);

    }

}
