package com.illumina.stratus.cadence.service.workflow;

import com.uber.cadence.workflow.Workflow;
import org.springframework.stereotype.Component;

/** GreetingWorkflow implementation that calls GreetingsActivities#printIt. */

public class GreetingWorkflowImpl implements GreetingWorkflow {

    /**
     * Activity stub implements activity interface and proxies calls to it to Cadence activity
     * invocations. Because activities are reentrant, only a single stub can be used for multiple
     * activity invocations.
     */
    private final GreetingActivities activities =
            Workflow.newActivityStub(GreetingActivities.class);

    @Override
    public String getGreeting(String name) {
        // This is a blocking call that returns only after the activity has completed.
        return activities.composeGreeting("Hello", name);
    }
}
