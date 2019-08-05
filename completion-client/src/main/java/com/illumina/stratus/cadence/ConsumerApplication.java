package com.illumina.stratus.cadence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.illumina.stratus.cadence.service.config.QueueConfiguration;
import com.illumina.stratus.cadence.service.model.TaskDto;
import com.uber.cadence.client.ActivityCompletionClient;
import com.uber.cadence.client.WorkflowClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@SpringBootApplication
@EnableRabbit
public class ConsumerApplication implements RabbitListenerConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

//    @Bean
//    public MappingJackson2MessageConverter receiverJackson2Converter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        return converter;
//    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        //factory.setMessageConverter(receiverJackson2Converter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Autowired
    private Receiver receiver;

}

@Service
class Receiver {


    static final String DOMAIN = "WES-ISL";

    private WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
    private ActivityCompletionClient completionClient = workflowClient.newActivityCompletionClient();


    @RabbitListener(queues = QueueConfiguration.RESULT_QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received task token<" + message + ">");
        TaskDto task;
        try {
            task = new ObjectMapper().readValue(message, TaskDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Can not deserialize the task message.");
        }

        String taskToken = task.getTaskToken();

        System.out.println("Sending final output to server.");

        completionClient.complete(taskToken.getBytes(), task.getOutput());
    }


}
