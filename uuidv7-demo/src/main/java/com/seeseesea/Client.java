package com.seeseesea;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

/**
 * Client
 */
public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            UUID uuid = UuidCreator.getTimeOrderedEpoch();
//            System.out.println(uuid.toString().replaceAll("-", ""));
            System.out.println(uuid);
        }
    }
}
