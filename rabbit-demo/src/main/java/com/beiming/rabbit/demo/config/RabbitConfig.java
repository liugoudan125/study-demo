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


    /**
     * 报关单货物申报（申明交换机）
     */
    @Bean
    public TopicExchange testExchange() {
        return new TopicExchange("test-exchange");
    }

    /**
     * 报关单货物申报（申明队列）
     */
    @Bean
    public Queue testQueue() {
        return new Queue("test-queue");
    }

    /**
     * 报关单货物申报（绑定交换机和队列）
     */
    @Bean
    public Binding testBinding(TopicExchange testExchange,
                                              Queue testQueue) {
        return BindingBuilder.bind(testQueue)
                .to(testExchange)
                .with("test-key");
    }



    /**
     * 报关单货物申报（申明交换机）
     */
    @Bean
    public TopicExchange customsGoodsDeclareTopicExchange() {
        return new TopicExchange(ExchangeEnum.CUSTOMS_GOODS_DECLAR_TOPIC_EXCHANGE.getName());
    }

    /**
     * 报关单货物申报（申明队列）
     */
    @Bean
    public Queue customsGoodsDeclareQueue() {
        return new Queue(QueueEnum.CUSTOMS_GOODS_DECLARE_QUEUE.getName());
    }

    /**
     * 报关单货物申报（绑定交换机和队列）
     */
    @Bean
    public Binding customsGoodsDeclareBinding(TopicExchange customsGoodsDeclareTopicExchange,
                                              Queue customsGoodsDeclareQueue) {
        return BindingBuilder.bind(customsGoodsDeclareQueue)
                .to(customsGoodsDeclareTopicExchange)
                .with(QueueEnum.CUSTOMS_GOODS_DECLARE_QUEUE.getRoutingKey());
    }

    /**
     * 报关单舱单申报（申明交换机）
     */
    @Bean
    public TopicExchange customsManifestDeclareTopicExchange() {
        return new TopicExchange(ExchangeEnum.CUSTOMS_MANIFEST_DECLAR_TOPIC_EXCHANGE.getName());
    }

    /**
     * 报关单舱单申报（申明队列）
     */
    @Bean
    public Queue customsManifestDeclareQueue() {
        return new Queue(QueueEnum.CUSTOMS_MANIFEST_DECLARE_QUEUE.getName());
    }

    /**
     * 报关单舱单申报（绑定交换机和队列）
     */
    @Bean
    public Binding customsManifestDeclareBinding(TopicExchange customsManifestDeclareTopicExchange,
                                                 Queue customsManifestDeclareQueue) {
        return BindingBuilder.bind(customsManifestDeclareQueue)
                .to(customsManifestDeclareTopicExchange)
                .with(QueueEnum.CUSTOMS_MANIFEST_DECLARE_QUEUE.getRoutingKey());
    }
}