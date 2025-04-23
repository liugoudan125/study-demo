package com.example.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @author lcl
 * @date 2023/9/21 13:06
 * @desc ExcelData
 */
@ContentRowHeight(20)
@HeadRowHeight(30)
@ColumnWidth(20)
@HeadStyle(
        borderBottom = BorderStyleEnum.MEDIUM,
        borderLeft = BorderStyleEnum.MEDIUM,
        borderRight = BorderStyleEnum.MEDIUM,
        borderTop = BorderStyleEnum.MEDIUM
)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,
        borderBottom = BorderStyleEnum.MEDIUM,
        borderLeft = BorderStyleEnum.MEDIUM,
        borderRight = BorderStyleEnum.MEDIUM,
        borderTop = BorderStyleEnum.MEDIUM,
        verticalAlignment = VerticalAlignmentEnum.CENTER
)
public class ExcelData {
    @ExcelProperty("提交数")
    @NumberFormat
    private Long count;
    @ExcelProperty("单价")
    @NumberFormat(value = "0.0000",roundingMode = RoundingMode.HALF_UP)
    //todo decimal格式化,四位小数
    private BigDecimal price;
    @ExcelProperty("时间")
    private LocalDate date;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExcelData{" +
                "count=" + count +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
