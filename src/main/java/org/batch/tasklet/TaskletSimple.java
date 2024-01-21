//package org.batch.tasklet;
//
//import org.batch.listener.FirstJobListener;
//import org.batch.tasklet.task.SecondTask;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//public class TaskletSimple {
//
//    @Autowired
//    private SecondTask secondTask;
//
//    @Autowired
//    private FirstJobListener firstJobListener;
//
//    @Bean
//    protected Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("firstJob", jobRepository)
//                .incrementer(new RunIdIncrementer())// auto increment job id to run job multiple times
//                .start(firstStep(jobRepository, transactionManager))
//                .next(secondStep(jobRepository, transactionManager))
//                .listener(firstJobListener)
//                .build();
//    }
//
//    private Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("firstStep", jobRepository)
//                .tasklet((StepContribution stepContribution, ChunkContext chunkContext) -> {
//                    System.out.println("this is first tasklet");
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//
//    }
//
//    private Step secondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("secondStep", jobRepository)
//                .tasklet(secondTask, transactionManager)
//                .build();
//    }
//}
