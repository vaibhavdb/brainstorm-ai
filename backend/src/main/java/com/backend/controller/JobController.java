package com.backend.controller;
import com.backend.entity.Job;
import com.backend.service.JobService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects/jobs")
@CrossOrigin(origins = "*") // allow frontend
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Start a new job
    @PostMapping
    public Job startJob(@RequestBody RepoRequest repoRequest) {
        return jobService.startJob(repoRequest.getRepoUrl());
    }

    // Check status of job
    @GetMapping("/{jobId}")
    public Job getJobStatus(@PathVariable String jobId) {
        return jobService.getJobStatus(jobId);
    }

    // --- DTO for request ---
    static class RepoRequest {
        private String repoUrl;
        public String getRepoUrl() { return repoUrl; }
        public void setRepoUrl(String repoUrl) { this.repoUrl = repoUrl; }
    }
}
