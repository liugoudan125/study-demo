package com.example.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lcl
 * @date 2023/9/21 13:07
 * @desc Main
 */
public class Main {
    public static void main(String[] args) {
        File file = new File("a.xlsx");
        Random random = new Random();
        List<ExcelData> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelData excelData = new ExcelData();
            excelData.setDate(LocalDate.now());
            excelData.setCount(random.nextLong());
            excelData.setPrice(BigDecimal.valueOf(random.nextDouble() * random.nextInt()));
            dataList.add(excelData);
            System.out.println(excelData);
        }
        EasyExcel.write(file.getAbsolutePath(), ExcelData.class)
                .sheet(1, "shouye")
                .doWrite(dataList);

        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println(decimalFormat.format(BigDecimal.valueOf(1.22)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(0.22344)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(0.22345)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(0.22346)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(0.22347)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(0.22349)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(1)));
        System.out.println(decimalFormat.format(BigDecimal.valueOf(1.252352)));
    }

}
