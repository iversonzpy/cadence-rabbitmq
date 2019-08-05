package com.illumina.stratus.cadence;


import com.illumina.stratus.cadence.service.workflow.GreetingActivities;
import com.illumina.stratus.cadence.service.workflow.GreetingActivitiesImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public GreetingActivities greetingActivities(){
        return new GreetingActivitiesImpl(rabbitTemplate);
    }
}
