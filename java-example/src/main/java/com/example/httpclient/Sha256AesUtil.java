package com.example.httpclient;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Sha256AesUtil {

    /**
     * HmacWithSHA256 加签
     *
     * @param key
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encodeHmacSHA256(byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] bytes = mac.doFinal(data);
        return Hex.encodeHexString(bytes);
    }


    /**
     * AES/ECB/PKCS5Padding 内容加密
     *
     * @param key
     * @param data
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrypt(byte[] key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");// byte[] key 转化为加密算法AES的key
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 加密模式
        byte[] bytes = cipher.doFinal(data);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * AES/ECB/PKCS5Padding 内容解密
     *
     * @param key
     * @param data
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(byte[] key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] byteData = Base64.decodeBase64(data);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");// byte[] key 转化为加密算法AES的key
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 解密模式
        byte[] bytes = cipher.doFinal(byteData);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, DecoderException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 选择128, 192, 或 256
        SecretKey secretKey = keyGen.generateKey();
        byte[] key = secretKey.getEncoded();
        System.out.println("Generated key length: " + key.length);
        System.out.println(Arrays.toString(key));
        byte[] bytes = Hex.decodeHex(Hex.encodeHexString(key));
        System.out.println("Generated key length: " + bytes.length);
        System.out.println(Arrays.toString(bytes));

        System.out.println(Hex.encodeHexString(key));


    }
}
