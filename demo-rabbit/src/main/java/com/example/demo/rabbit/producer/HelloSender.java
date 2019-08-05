package com.example.demo.rabbit.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 用于单生产者-》单消费者测试
     */
    public void send() {
        String sendMsg = "hello " + new Date();
        System.out.println("Sender : " + sendMsg);
        this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }
}