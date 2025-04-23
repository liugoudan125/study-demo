package com.beiming.mongo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Car
 */
@Setter
@Getter
@ToString
public class Car {

    private String name;
    private String color;
    private Long price;
    private Integer num;
}
