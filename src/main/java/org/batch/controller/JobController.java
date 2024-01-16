package org.batch.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {

    private final JobLauncher jobLauncher;
    Job firstJob;
    Job firstChunkJob;

    public JobController(JobLauncher jobLauncher, Job firstJob, Job firstChunkJob) {
        this.jobLauncher = jobLauncher;
        this.firstJob = firstJob;
        this.firstChunkJob = firstChunkJob;
    }

    @GetMapping("/start/{jobName}")
    private String startJob(@PathVariable String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // Init parameters job ex1:
//        Map<String, JobParameter<?>> params = new HashMap<>();
//        params.put("paramLong", new JobParameter<>(System.currentTimeMillis(), Long.class));
//        JobParameters jobParameters1 = new JobParameters(params);

        // Init parameters job ex2:
        JobParametersBuilder  builder = new JobParametersBuilder();
        builder.addString("currentTime", String.valueOf(System.currentTimeMillis()));
        JobParameters jobParameters = builder.toJobParameters();

        if (jobName.equals("firstJob")) {
            jobLauncher.run(firstJob, jobParameters);
        } else if (jobName.equals("firstChunkJob")) {
            jobLauncher.run(firstChunkJob, jobParameters);
        }
        return "Job started";
    }
}
