package com.example.bigdecimal;

import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author lcl
 * @date 2023/8/14 15:44
 * @desc BigDecimal
 */
public class BigDecimalUtil {


    /**
     * 检查bigDecimal是否为空，为空则返回一个为0的BigDecimal,并设置为精度为4个小数点，四舍五入
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal checkBigDecimal(BigDecimal bigDecimal) {
        return (null == bigDecimal ? BigDecimal.ZERO : bigDecimal).setScale(4, RoundingMode.HALF_UP);
    }

}
