package com.illumina.stratus.cadence.service.workflow;

import com.uber.cadence.workflow.WorkflowMethod;

import static com.illumina.stratus.cadence.service.workflow.PropertiesConstant.TASK_LIST;

public interface GreetingWorkflow {
    /** @return greeting string */
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 1500, taskList = TASK_LIST)
    String getGreeting(String name);
}