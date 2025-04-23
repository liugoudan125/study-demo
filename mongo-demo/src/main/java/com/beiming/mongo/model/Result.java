package com.beiming.mongo.model;

import lombok.Data;

/**
 * Result
 */
@Data
public class Result {

    private ID id;

    private long isRed;
    private long isNotRed;
    private long count;
    private long totalPrice;

    @Data
    public static class ID {

        private Integer age;
//        private String color;
    }

}
