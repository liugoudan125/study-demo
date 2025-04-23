package com.beiming.rabbit.demo.controller;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.UUID;

/**
 * Controller
 */
@RestController
@RequestMapping("mq")
public class Controller {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @PostMapping("sendMsg")
    public String sendMsg(@RequestParam("key") String key) {
        String msg = String.valueOf(Clock.systemUTC().millis());

        for (int i = 0; i < 1000; i++) {
            String messageId = UUID.randomUUID().toString();
            rabbitTemplate.convertAndSend("test", key, MessageBuilder
                    .withBody(msg.getBytes(StandardCharsets.UTF_8))
                    .setMessageCount(5)
                    .setMessageIdIfAbsent(messageId)
                    .setReceivedRoutingKeyIfAbsent(key)
                    .setType("aaa")
                    .build());
        }


        return msg;
    }

}
