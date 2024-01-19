package org.batch.service;

import org.batch.params.JobParams;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class JobService {

    private final JobLauncher jobLauncher;
    Job firstJob;
    Job firstChunkJob;

    public JobService(JobLauncher jobLauncher, Job firstJob, Job firstChunkJob) {
        this.jobLauncher = jobLauncher;
        this.firstJob = firstJob;
        this.firstChunkJob = firstChunkJob;
    }

    @Async
    public void executeJob(String jobName, List<JobParams> jobParams) {
        try {
            // Init parameters job ex1:
            //  Map<String, JobParameter<?>> params = new HashMap<>();
            //  params.put("paramLong", new JobParameter<>(System.currentTimeMillis(), Long.class));
            //  JobParameters jobParameters1 = new JobParameters(params);

            // Init parameters job ex2:
            JobExecution jobExecution = null;
            JobParametersBuilder builder = new JobParametersBuilder();
//            builder.addString("currentTime", String.valueOf(System.currentTimeMillis()));
            jobParams.forEach(jobParam ->
                    builder.addString(jobParam.getParamKey(), jobParam.getParamValue())
            );
            JobParameters jobParameters = builder.toJobParameters();

            if (jobName.equals("firstJob")) {
                jobExecution = jobLauncher.run(firstJob, jobParameters);
            } else if (jobName.equals("firstChunkJob")) {
                jobExecution = jobLauncher.run(firstChunkJob, jobParameters);
            }
            System.out.printf("""
                    JobExecution:
                    id: %s
                    job Id: %s
                    job status : %s
                    %n""", jobExecution.getId(), jobExecution.getJobId(), jobExecution.getStatus());

        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            e.getStackTrace();
            System.out.println("Exception while run job");
        }
    }
}
