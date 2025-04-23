package online.goudan.mq.lisenter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author goudan
 * @date 2023/7/27 17:47
 * @desc MyMQListener
 */
@RocketMQMessageListener(consumerGroup = "GOUDDD", selectorType = SelectorType.TAG, selectorExpression = "test", topic = "test-goudan")
@Slf4j
@Component
public class MyMQListener implements RocketMQListener<String> {
    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }
}
