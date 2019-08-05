package com.illumina.stratus.cadence.service.workflow;

import com.illumina.stratus.cadence.service.config.QueueConfiguration;
import com.illumina.stratus.cadence.service.model.TaskDto;
import com.uber.cadence.activity.Activity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class GreetingActivitiesImpl implements GreetingActivities {


    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        return input;
    }

    public void sendMessage(byte[] taskToken, String result) {
        TaskDto task = new TaskDto(new String(taskToken), result, null);
        this.rabbitTemplate.convertAndSend(QueueConfiguration.EXCHANGE_NAME, QueueConfiguration.REQUEST_ROUTING_KEY, task.toInputString());
    }
}
