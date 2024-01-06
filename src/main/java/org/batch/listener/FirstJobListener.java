package org.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before Job: " + jobExecution.getJobInstance().getJobName() + " is starting");
        System.out.println("Job parameters: " + jobExecution.getJobParameters());
        System.out.println("Job ex context: " + jobExecution.getExecutionContext());

        jobExecution.getExecutionContext().put("key", "value");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After Job: " + jobExecution.getJobInstance().getJobName() + " is starting");
        System.out.println("Job parameters: " + jobExecution.getJobParameters());
        System.out.println("Job ex context: " + jobExecution.getExecutionContext());
    }
}
