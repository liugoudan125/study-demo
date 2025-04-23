package com.beiming.controller;

import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MsgSendController
 */
@RestController
@RequestMapping("msg")
@CrossOrigin
public class MsgSendController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("send")
    public String send(String selector) {
        String topic = "mqtt:%s".formatted(selector);
        Message<String> message = MessageBuilder.withPayload(
                LocalDateTime.now().format(dateTimeFormatter)
        ).build();
        SendResult sendResult = rocketMQTemplate.syncSend(
                topic,
                message,
                3000,
                2
        );
        return sendResult.toString();
    }

    @GetMapping("aa")
    public String aa(String msg) {
        if (msg == null) {
            throw new RuntimeException();
        }
        return msg;
    }
}
