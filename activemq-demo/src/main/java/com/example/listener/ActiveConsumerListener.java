package com.example.listener;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author
 * @date 2023/4/4 11:59
 * @desc ActiveConsumerListener
 */
@Component
public class ActiveConsumerListener {


    @JmsListener(destination = "websocket_message")
    public void acept(String msg) {
        try {
            System.out.println("消费者：" + msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
