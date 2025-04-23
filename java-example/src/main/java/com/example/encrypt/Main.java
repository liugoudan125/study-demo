package com.example.encrypt;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;

/**
 * @author
 * @date 2023/5/30 11:31
 * @desc Main
 */
public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\lcl94\\Downloads\\神途從氪星開始.txt")));
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\lcl94\\Downloads\\神途从氪星开始.txt");

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("繁体: " + line);
            line = ZhConverterUtil.convertToSimple(line);
            System.out.println("简体: " + line);
            writer.write(line);
        }
        outputStream.close();
        bufferedReader.close();
    }

}
