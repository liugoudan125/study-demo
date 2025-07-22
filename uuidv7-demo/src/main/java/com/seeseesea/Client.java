package com.seeseesea;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Client
 */
public class Client {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            UUID uuid = UuidCreator.getTimeOrderedEpoch();
            list.add(uuid.toString());
        }

        List<String> list2 = new ArrayList<>(List.copyOf(list));
        list2.sort(String::compareTo);
        for (int i = 0; i < 100; i++) {
            System.out.printf("%s | %s%n", list.get(i), list2.get(i));
            System.out.println(list.get(i).equals(list2.get(i)));

        }
    }
}
