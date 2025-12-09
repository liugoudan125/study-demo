package com.beiming.rabbit.demo.config;


/**
 * 队列配置枚举
 * 注：队列枚举统一用“_QUEUE”结尾，前面部分与队列名保持一致
 */
public enum QueueEnum {
    /**
     * 报关单货物申报
     */
    CUSTOMS_GOODS_DECLARE_QUEUE("customs.goods.declare", "customs.goods.declare"),

    /**
     * 报关单舱单申报
     */
    CUSTOMS_MANIFEST_DECLARE_QUEUE("customs.manifest.declare", "customs.manifest.declare"),
    ;


    /**
     * 队列名称
     */
    private final String name;

    /**
     * 队列路由键
     */
    private final String routingKey;

    QueueEnum(String name, String routingKey) {
        this.name = name;
        this.routingKey = routingKey;
    }

    public String getName() {
        return name;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}