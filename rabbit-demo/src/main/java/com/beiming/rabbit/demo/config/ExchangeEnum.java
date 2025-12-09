package com.beiming.rabbit.demo.config;


/**
 * rabbitmq交换配置枚举
 * 注：交换配置统一用“交换配置类型”+“_EXCHANGE”结尾，前面部分与交换配置名前缀保持一致
 */
public enum ExchangeEnum {

    /**
     * 报关单货物申报
     */
    CUSTOMS_GOODS_DECLAR_TOPIC_EXCHANGE("customs.goods.declare.topic.exchange"),

    /**
     * 报关单舱单申报
     */
    CUSTOMS_MANIFEST_DECLAR_TOPIC_EXCHANGE("customs.manifest.declare.topic.exchange"),
    ;


    /**
     * 交换机名称
     */
    private final String name;

    ExchangeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}