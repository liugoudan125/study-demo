package com.beiming;

import com.github.liuyueyi.quick.transfer.ChineseUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Tw2s-2024/3/19-16:48
 */
public class Tw2s {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\lcl\\Downloads\\dd.txt");
        File outFile = new File("C:\\Users\\lcl\\Downloads\\dds.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            String line = null;
            while (null != (line = reader.readLine())) {
                line = ChineseUtils.tw2s(line);
                writer.write(line + "\n");
            }
        }

    }

}
