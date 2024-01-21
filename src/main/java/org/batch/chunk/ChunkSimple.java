package org.batch.chunk;

import org.batch.chunk.ItemProcessor.FirstItemProcessor;
import org.batch.chunk.ItemReader.FirstItemReader;
import org.batch.chunk.itemWriter.FirstItemWriter;
import org.batch.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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

    //    private Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("firstChunkStep", jobRepository)
//                .<Integer, Long> chunk(3, transactionManager) // write 3 items at a time to the database
//                .reader(firstItemReader)
//                .processor(firstItemProcessor)
//                .writer(firstItemWriter)
//                .build();
//    }
    private Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("firstChunkStep", jobRepository)
                .<Student, Student>chunk(3, transactionManager) // write 3 items at a time to the database
                .reader(flatFileItemReaderStudent())
//                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    // job read csv file using flatfileitemreader
    public FlatFileItemReader<Student> flatFileItemReaderStudent() {
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("student.csv"));

        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        tokenizer.setNames("id", "firstName", "lastName", "email");
        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }

}
