package com.beiming.rabbit.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.util.HashMap;

/**
 * RabbitConfig
 */
@Configuration
public class RabbitConfig {


    @Bean
    public Queue errorQueue() {
        return new Queue("test-error", true, false, false, new HashMap<String, Object>() {
            {
                put("x-queue-type", "quorum");
            }
        });
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("test");
    }

    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue())
                .to(exchange())
                .with("test:error");
    }

    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "test", "test:error");
    }
}
