package com.backend.entity;

import java.util.UUID;

public class Job {
    private String jobId;
    private String status; // QUEUED, RUNNING, DONE, FAILED
    private String progressNote;
    private int progress;
    private Object result;

    public Job() {
        this.jobId = UUID.randomUUID().toString();
        this.status = "QUEUED";
        this.progress = 0;
    }


    public String getJobId() { return jobId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getProgressNote() { return progressNote; }
    public void setProgressNote(String progressNote) { this.progressNote = progressNote; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
    public Object getResult() { return result; }
    public void setResult(Object result) { this.result = result; }
}
