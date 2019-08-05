package com.illumina.stratus.cadence;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.illumina.stratus.cadence.service.config.QueueConfiguration;
import com.illumina.stratus.cadence.service.model.TaskDto;
import com.uber.cadence.client.ActivityCompletionClient;
import com.uber.cadence.client.WorkflowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;


/**
 * Listening to the result queue
 */

@Slf4j
public class CadenceCompletionClient {

    static final String DOMAIN = "WES-ISL";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
    private ActivityCompletionClient completionClient = workflowClient.newActivityCompletionClient();

    /**
     * Change here to result queue
     * @param message
     */
    @RabbitListener(queues = QueueConfiguration.RESULT_QUEUE_NAME)
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

        String taskToken = task.getTaskToken();

        completionClient.complete(taskToken.getBytes(), task.getOutput());

    }

}
