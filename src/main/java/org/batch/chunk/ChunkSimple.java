package org.batch.chunk;

import org.batch.chunk.ItemProcessor.FirstItemProcessor;
import org.batch.chunk.ItemReader.FirstItemReader;
import org.batch.chunk.itemWriter.FirstItemWriter;
import org.batch.model.StudentCSV;
import org.batch.model.StudentJson;
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
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
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
                .<StudentJson, StudentJson>chunk(3, transactionManager) // write 3 items at a time to the database
//                .reader(flatFileItemReaderStudent()) // reader student csv
                .reader(jsonItemReaderStudent()) // reader student json
//                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    // job read csv file using flatfileitemreader
    public FlatFileItemReader<StudentCSV> flatFileItemReaderStudent() {
        FlatFileItemReader<StudentCSV> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("student.csv"));

        DefaultLineMapper<StudentCSV> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        tokenizer.setNames("id", "firstName", "lastName", "email");
        BeanWrapperFieldSetMapper<StudentCSV> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(StudentCSV.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }

    public JsonItemReader<StudentJson> jsonItemReaderStudent() {
        JsonItemReader<StudentJson> reader = new JsonItemReader<>();
        reader.setResource(new ClassPathResource("student.json"));
        reader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
        return reader;
    }

}
