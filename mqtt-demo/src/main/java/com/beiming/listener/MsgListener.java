package com.beiming.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MsgListener
 */
@Component
@RocketMQMessageListener(topic = "mqtt", selectorExpression = "lcl", consumerGroup = "${rocketmq.consumer.group}")
public class MsgListener implements RocketMQListener<String> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onMessage(String message) {
        System.out.printf("%s%n%s%n", message, LocalDateTime.now().format(dateTimeFormatter));
        System.out.println("=========================================================");
    }
}
