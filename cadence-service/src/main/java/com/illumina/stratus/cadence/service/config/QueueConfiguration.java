package com.illumina.stratus.cadence.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueueConfiguration {

    public static final String EXCHANGE_NAME = "wes-isl-exchange";
    public static final String REQUEST_QUEUE_NAME = "wes-isl-request-queue";
    public static final String RESULT_QUEUE_NAME = "wes-isl-result-queue";
    public static final String REQUEST_ROUTING_KEY = "wes.isl";
    public static final String RESULT_ROUTING_KEY = "wes.isl";

    @Bean
    public TopicExchange islTopicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE_NAME);
    }

    @Bean
    public Queue resultQueue() {
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
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
