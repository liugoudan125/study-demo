package com.example.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @date 2023/4/7 18:32
 * @desc OrderReq
 */
public class OrderReq {

    @Data
    public static class CreateDTO implements Serializable {
        private static final long serialVersionUID = 2129073213607627375L;
        private Integer userId;
        private Integer productId;
    }
}
