package com.beiming.rabbit.demo.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Clock;

/**
 * TestMqListener
 */
@Component
public class TestMqListener {

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "test",
//                    arguments = {
//                            @Argument(name = "x-queue-type", value = "quorum")
//                    }),
//            exchange = @Exchange(name = "test", type = ExchangeTypes.TOPIC),
//            key = "test"
//    ))
//    @RabbitListener(queues = "test-queue")
    @RabbitListener(queues = "customs.goods.declare")
//    public void handlerMessage(String msg, Channel channel, Message message) throws Exception {
    public void handlerMessage(Message message) throws Exception {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println(msg);
    }
}