package com.illumina.stratus.cadence.service.mqservice;

import com.illumina.stratus.cadence.service.config.QueueConfiguration;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class MqSender {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendToRequestRabbitmq(String message){

        this.rabbitMessagingTemplate.convertAndSend(QueueConfiguration.EXCHANGE_NAME, QueueConfiguration.REQUEST_ROUTING_KEY, message);
    }

    public void sendToResultRabbitmq(String message){

        this.rabbitMessagingTemplate.convertAndSend(QueueConfiguration.EXCHANGE_NAME, QueueConfiguration.RESULT_ROUTING_KEY, message);
    }
}
