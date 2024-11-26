package com.javaMsProject.jobMs.job.mapper;

import com.javaMsProject.jobMs.job.Job;
import com.javaMsProject.jobMs.job.dto.JobDTO;
import com.javaMsProject.jobMs.job.external.Company;
import com.javaMsProject.jobMs.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews){
        JobDTO JobDTO = new JobDTO();
        JobDTO.setTitle(job.getTitle());
        JobDTO.setDescription(job.getDescription());
        JobDTO.setLocation(job.getLocation());
        JobDTO.setMinSalary(job.getMinSalary());
        JobDTO.setMaxSalary(job.getMaxSalary());
        JobDTO.setCompany(company);
        JobDTO.setReviews(reviews);

        return JobDTO;
    }
}
