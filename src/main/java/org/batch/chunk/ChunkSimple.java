package org.batch.chunk;

import org.batch.chunk.ItemProcessor.FirstItemProcessor;
import org.batch.chunk.ItemReader.FirstItemReader;
import org.batch.chunk.itemWriter.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChunkSimple {

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
    protected Job firstChunkJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
       return new JobBuilder("firstChunkJob", jobRepository)
               .incrementer(new RunIdIncrementer())
               .start(firstChunkStep(jobRepository, transactionManager))
               .build();
    }

    private Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("firstChunkStep", jobRepository)
                .<Integer, Long> chunk(3, transactionManager) // write 3 items at a time to the database
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

}
