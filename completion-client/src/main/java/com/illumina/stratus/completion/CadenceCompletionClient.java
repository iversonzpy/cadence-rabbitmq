package com.illumina.stratus.completion;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.illumina.stratus.model.TaskDto;
import com.uber.cadence.client.ActivityCompletionClient;
import com.uber.cadence.client.WorkflowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Listening to the result queue
 */

@Slf4j
@Component
@RabbitListener(queues = "result-queue")
public class CadenceCompletionClient {

    static final String DOMAIN = "WES-ISL";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
    private ActivityCompletionClient completionClient = workflowClient.newActivityCompletionClient();

    @RabbitHandler
    public void receiveMessage(String message) {


        System.out.println("Completion Client.");
        System.out.println("Received message: <" + message + ">");

        // get task token from message

        TaskDto task = null;
        try {
            task = MAPPER.readValue(message, TaskDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(task == null) {
            throw new RuntimeException("Error: Result is null.");
        }

        completionClient.complete(task.getTaskToken().getBytes(), task.getOutput());

    }

}
