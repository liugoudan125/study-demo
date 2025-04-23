package com.example.encrypt.util;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author goudan
 * @date 2023/4/23 17:13
 * @desc CustomEncryptUtil
 */
public class CustomEncryptUtil {


    private static String decrypt(String key, String encryptStr) {
        byte[] mix = generataSa(key);
        byte[] encryptBytes = encryptStr.getBytes(StandardCharsets.UTF_8);
        byte[] decryptBytes = new byte[encryptBytes.length / 2];
        for (int i = 0; i < decryptBytes.length; i++) {
            decryptBytes[i] = (byte) (encryptBytes[2 * i] ^ mix[encryptBytes[2 * i + 1]]);
        }
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }

    private static byte[] encrypt(String key, String str) {
        byte[] mix = generataSa(key);
        byte[] originBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] encryptBytes = new byte[originBytes.length * 2];
        Random random = new Random();
        for (int i = 0; i < originBytes.length; i++) {
            byte index = (byte) random.nextInt(mix.length);
            encryptBytes[2 * i] = (byte) (originBytes[i] ^ mix[index]);
            encryptBytes[2 * i + 1] = index;
        }
        return encryptBytes;
    }

    private static byte[] generataSa(String key) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 256) {
            throw new IllegalArgumentException("key值过长");
        }
        byte[] mix = new byte[bytes.length - 1];
        for (int i = 0; i < bytes.length - 1; i++) {
            mix[i] = (byte) (bytes[i] & bytes[i + 1]);
            mix[i] = (byte) (mix[i] | bytes[i]);
        }
        return mix;
    }


}
