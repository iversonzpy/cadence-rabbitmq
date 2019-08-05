package com.illumina.stratus.cadence;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.illumina.stratus.cadence.service.config.QueueConfiguration;
import com.illumina.stratus.cadence.service.model.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Listen to requests queue
 * Send result to result queue
 */
@Slf4j
@Component
@RabbitListener(queues = QueueConfiguration.REQUEST_QUEUE_NAME)
public class RemoteCadenceActivityPoller {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @RabbitHandler
    public void receiveMessage(String message) {

        System.out.println("Remote Poller.");
        System.out.println("Received message: <" + message + ">");

        // get task token from message

        TaskDto task;
        try {
            task = MAPPER.readValue(message, TaskDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Can not deserialize the task message.");
        }

        doWork(task);
        // sendSucceedMessage(task);

        String result = "{\"hello\":456}";
        task.setOutput(result);

        System.out.println("After work: ");
        System.out.println(task.toString());
        System.out.println("DONE");

        System.out.println("========sending to result queue====");
        sendSucceedMessage(task);

    }


    private static void doWork(TaskDto task) {
        try {
            Thread.sleep(2000); // simulate a work, sleep for 2 seconds
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }

    }

    private void sendSucceedMessage(TaskDto task) {

        rabbitTemplate.convertAndSend(QueueConfiguration.EXCHANGE_NAME, QueueConfiguration.RESULT_ROUTING_KEY, task.toString());
    }




}
