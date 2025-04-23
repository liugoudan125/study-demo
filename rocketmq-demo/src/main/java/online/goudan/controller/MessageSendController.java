package online.goudan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date 2023/7/27 10:31
 * @desc MessageSendController
 */
@Api(tags = "测试模块")
@RestController
@RequestMapping("msgSend")
@Slf4j
public class MessageSendController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "msg", value = "账单ID", required = true),
            @ApiImplicitParam(name = "id", value = "账单ID", required = true),
            @ApiImplicitParam(name = "name", value = "账单ID", required = true)

    })
    @ApiOperation(value = "根据ID查询账单")
    @GetMapping("/{msg}")
    public String sendMsg(@PathVariable("msg") String msg, @RequestParam("id") Long id, @RequestParam("name") String na) {
        rocketMQTemplate.asyncSend("test-goudan:test", MessageBuilder.withPayload(msg).build(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("MessageSendController.onSuccess:  {}", sendResult);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        log.error("消息发送失败", throwable);
                    }
                });
        return msg;
    }

}
