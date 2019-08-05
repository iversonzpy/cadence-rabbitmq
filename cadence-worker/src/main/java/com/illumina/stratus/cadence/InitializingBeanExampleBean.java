package com.illumina.stratus.cadence;

import com.illumina.stratus.cadence.service.workflow.GreetingActivities;
import com.illumina.stratus.cadence.service.workflow.GreetingActivitiesImpl;
import com.illumina.stratus.cadence.service.workflow.GreetingWorkflowImpl;
import com.uber.cadence.worker.Worker;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializingBeanExampleBean implements InitializingBean {

////    private static final Logger LOG
//            = Logger.getLogger(InitializingBeanExampleBean.class);

//    @Autowired
//    private Environment environment;


    @Autowired
    private GreetingActivities greetingActivities;

    static final String DOMAIN = "WES-ISL";
    static final String TASK_LIST = "HelloAsyncActivityCompletion";

    @Override
    public void afterPropertiesSet() throws Exception {



        Worker.Factory factory = new Worker.Factory(DOMAIN);
        Worker worker = factory.newWorker(TASK_LIST);
        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        // CompletionClient is passed to activity here only to support unit testing.

        worker.registerActivitiesImplementations(greetingActivities);
        // Start listening to the workflow and activity task lists.
        factory.start();
        System.out.println("Worker started listening to the workflow and activity task lists");

    }
}