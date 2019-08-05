package com.illumina.stratus.cadence.service.config;

import com.illumina.stratus.cadence.service.mqservice.MqSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class QueueConfiguration {

    public static final String EXCHANGE_NAME = "wes-isl-exchange";
    public static final String REQUEST_QUEUE_NAME = "wes-isl-request-queue";
    public static final String RESULT_QUEUE_NAME = "wes-isl-result-queue";
    public static final String REQUEST_ROUTING_KEY = "wes.isl.request";
    public static final String RESULT_ROUTING_KEY = "wes.isl.result";


//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory =
//                new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }

    @Bean
    public TopicExchange islTopicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue requestQueue() {
        System.out.println("Created request queue");
        return new Queue(REQUEST_QUEUE_NAME);
    }

    @Bean
    public Queue resultQueue() {

        System.out.println("Created result queue");
        return new Queue(RESULT_QUEUE_NAME);
    }

    @Bean
    public Binding declareBindingRequest() {
        return BindingBuilder.bind(requestQueue()).to(islTopicExchange()).with(REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding declareBindingResult() {
        return BindingBuilder.bind(resultQueue()).to(islTopicExchange()).with(RESULT_ROUTING_KEY);
    }

    // You can comment the two methods below to use the default serialization / deserialization (instead of JSON)
//    @Bean
//    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//        return rabbitTemplate;
//    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}
