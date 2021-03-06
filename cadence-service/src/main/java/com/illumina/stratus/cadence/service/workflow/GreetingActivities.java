package com.illumina.stratus.cadence.service.workflow;


import com.uber.cadence.activity.ActivityMethod;
import org.springframework.stereotype.Component;

/** Activity interface is just a POJI. * */

public interface GreetingActivities {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 1000)
    String composeGreeting(String greeting, String name);
}