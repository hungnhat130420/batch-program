package org.batch.controller;

import org.batch.params.JobParams;
import org.batch.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {
    private JobService jobService;
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/start/{jobName}")
    private String startJob(@PathVariable String jobName,
                            @RequestBody List<JobParams> jobParamsRequest) {

        jobService.executeJob(jobName, jobParamsRequest);
        return "Job started";
    }
}
