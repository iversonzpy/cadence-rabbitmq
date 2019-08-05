package com.illumina.stratus.cadence.service.workflow;

import com.illumina.stratus.cadence.service.config.QueueConfiguration;
import com.illumina.stratus.cadence.service.model.TaskDto;
import com.illumina.stratus.cadence.service.mqservice.MqSender;
import com.illumina.stratus.cadence.service.util.SpringUtil;
import com.uber.cadence.activity.Activity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class GreetingActivitiesImpl implements GreetingActivities {

    public GreetingActivitiesImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final RabbitTemplate rabbitTemplate;

    // private MqSender mqSender = SpringUtil.getBean(MqSender.class);

    /**
     * Demonstrates how to implement an activity asynchronously. When {@link
     * Activity#doNotCompleteOnReturn()} is called the activity implementation function returning
     * doesn't complete the activity.
     */
    @Override
    public String composeGreeting(String greeting, String name) {
        // TaskToken is a correlation token used to match an activity task with its cadence
        byte[] taskToken = Activity.getTaskToken();
        System.out.println(new String(taskToken));
        // {id:xxx}

        // sent task token message

        String input = "{\"helloworld\":123}";

        sendMessage(taskToken, input);

        Activity.doNotCompleteOnReturn();
        // When doNotCompleteOnReturn() is invoked the return value is ignored.
        return "ignored";
    }

    public void sendMessage(byte[] taskToken, String input) {

        TaskDto task = new TaskDto(taskToken, input);

        this.rabbitTemplate.convertAndSend(QueueConfiguration.EXCHANGE_NAME, QueueConfiguration.REQUEST_ROUTING_KEY, task.toString());

//        mqSender.sendToRequestRabbitmq(task.toInputString());

        System.out.println("=============");
        System.out.println(task.toString());

        System.out.println("Message send to request queue");
    }
}
