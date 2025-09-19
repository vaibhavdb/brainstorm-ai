package com.backend.service;

import com.backend.entity.Job;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JobService {
    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    public Job startJob(String repoUrl) {
        Job job = new Job();
        job.setProgressNote("Starting summarization for " + repoUrl);
        job.setStatus("RUNNING");

        jobs.put(job.getJobId(), job);

        // fake async processing (for demo)
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                job.setProgress(100);
                job.setStatus("DONE");
                job.setProgressNote("Completed");
                job.setResult(Map.of(
                        "repo", repoUrl,
                        "branch", "main",
                        "projectSummary", "This is a fake summary for demo purposes.",
                        "fileSummaries", new String[]{"File1.java summary...", "File2.js summary..."},
                        "generatedAt", java.time.Instant.now().toString()
                ));
            } catch (InterruptedException e) {
                job.setStatus("FAILED");
                job.setProgressNote("Job interrupted");
            }
        }).start();

        return job;
    }

    public Job getJobStatus(String jobId) {
        return jobs.getOrDefault(jobId, null);
    }
}
