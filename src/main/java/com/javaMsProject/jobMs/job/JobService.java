package com.javaMsProject.jobMs.job;

import com.javaMsProject.jobMs.job.dto.JobDTO;
//import com.javaMsProject.jobMs.job.dto.JobWithComponyDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO findJob(long jobId);
    boolean deleteJob(long jobId);  // New method for deleting a job by ID
    boolean updateJob(long jobId, Job updatedJob);  // Method to update job

}
