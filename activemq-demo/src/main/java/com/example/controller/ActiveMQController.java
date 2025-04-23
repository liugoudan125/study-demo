package com.example.controller;

import com.example.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author
 * @date 2023/4/4 11:23
 * @desc ActiveMQController
 */
@RestController
@RequiredArgsConstructor
public class ActiveMQController {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @PostMapping(value = "sendMsg")
    public Student sendMsg(String msg) throws JsonProcessingException {
        Student student = new Student("liuchenglong", "数据字典学位表");
        jmsTemplate.convertAndSend("websocket_message", objectMapper.writeValueAsString(student));
        return student;
    }
}
