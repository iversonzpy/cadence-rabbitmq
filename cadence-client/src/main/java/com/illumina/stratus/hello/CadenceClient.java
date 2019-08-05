package com.illumina.stratus.hello;

import com.uber.cadence.client.WorkflowClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Initialize a workflow, run workflow method
 */
public class CadenceClient {


    static final String DOMAIN = "WES-ISL";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);

        GreetingWorkflow workflow = workflowClient.newWorkflowStub(GreetingWorkflow.class);
        // Execute a workflow returning a future that can be used to wait for the workflow
        // completion.
        CompletableFuture<String> greeting = WorkflowClient.execute(workflow::getGreeting, "World");

        // Wait for workflow completion.
        System.out.println(greeting.get());
        System.exit(0);
    }
}
