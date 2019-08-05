package com.illumina.stratus.remote;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.illumina.stratus.model.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Listen to requests queue
 * Send result to result queue
 */
@Slf4j
@Component
@RabbitListener(queues = "request-queue")
public class RemoteCadenceActivityPoller {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @RabbitHandler
    public void receiveMessage(String message) {

        System.out.println("Remote Poller.");
        System.out.println("Received message: <" + message + ">");

        // get task token from message

        TaskDto task = null;
        try {
            task = MAPPER.readValue(message, TaskDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(task == null) {
            throw new RuntimeException("Error: Request is null.");
        }

        doWork(task);
        // sendSucceedMessage(task);

        System.out.println("DONE");
    }



    private static void doWork(TaskDto task) {
        try {
            Thread.sleep(2000); // simulate a work, sleep for 2 seconds
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
        String result = "{\"hello\":456}";
        task.setOutput(result);

    }

    private void sendSucceedMessage(TaskDto task) {

        rabbitTemplate.convertAndSend("result-queue", "result-topic", task.toOutputString());
    }




}
