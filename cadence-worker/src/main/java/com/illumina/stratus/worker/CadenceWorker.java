package com.illumina.stratus.worker;

import com.illumina.stratus.hello.GreetingActivitiesImpl;
import com.illumina.stratus.hello.GreetingWorkflowImpl;
import com.uber.cadence.worker.Worker;

/**
 * Workers with registered activities
 */
public class CadenceWorker {

    static final String DOMAIN = "WES-ISL";
    static final String TASK_LIST = "HelloAsyncActivityCompletion";

    public static void main(String[] args) {
        Worker.Factory factory = new Worker.Factory(DOMAIN);
        Worker worker = factory.newWorker(TASK_LIST);
        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        // CompletionClient is passed to activity here only to support unit testing.

        worker.registerActivitiesImplementations(new GreetingActivitiesImpl());
        // Start listening to the workflow and activity task lists.
        factory.start();
        System.out.println("Worker started listening to the workflow and activity task lists");
    }
}
