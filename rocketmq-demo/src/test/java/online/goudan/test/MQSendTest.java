package online.goudan.test;

import lombok.extern.slf4j.Slf4j;
import online.goudan.RocketmqApplication;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author goudan
 * @date 2023/7/31 11:10
 * @desc MQSendTest
 */
@SpringBootTest(classes = RocketmqApplication.class)
@Slf4j
public class MQSendTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Test
    public void batchSendMsg() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        StopWatch stopWatch = new StopWatch("消息发送");
        int num = 100;
        batchSendMsg(100);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        stopWatch.start();
        for (int i = 0; i < num; i++) {
            executorService.execute(() -> {
                try {
                    batchSendMsg(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();
        stopWatch.stop();
        System.out.println("用时：" + stopWatch.getLastTaskTimeMillis());
    }

    private void batchSendMsg(int num) throws InterruptedException {
        Random random = new Random();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<Message<Long>> msgList = new ArrayList<>();
        for (int j = 0; j < num; j++) {
            msgList.add(MessageBuilder.withPayload(random.nextLong()).build());
        }
        rocketMQTemplate.asyncSend("test-goudan:test", msgList, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("MQSendTest.onSuccess: 消息批量发送成功 {}", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                countDownLatch.countDown();
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("发送失败", throwable);
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }
}
