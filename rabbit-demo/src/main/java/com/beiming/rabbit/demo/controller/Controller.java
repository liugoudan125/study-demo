package com.beiming.rabbit.demo.controller;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("sendMsg")
    public String sendMsg(@RequestParam("msg") String msg) {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend("customs.goods.declare.topic.exchange",
                    "customs.goods.declare", msg);
        }
        return msg;
    }

}