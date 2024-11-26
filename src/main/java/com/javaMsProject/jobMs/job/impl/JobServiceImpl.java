package com.javaMsProject.jobMs.job.impl;

import com.javaMsProject.jobMs.job.Job;
import com.javaMsProject.jobMs.job.JobRepository;
import com.javaMsProject.jobMs.job.JobService;
import com.javaMsProject.jobMs.job.clients.CompanyClient;
import com.javaMsProject.jobMs.job.clients.ReviewClient;
import com.javaMsProject.jobMs.job.dto.JobDTO;
import com.javaMsProject.jobMs.job.external.Company;
import com.javaMsProject.jobMs.job.external.Review;
import com.javaMsProject.jobMs.job.mapper.JobMapper;
import com.netflix.discovery.converters.Auto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    RestTemplate restTemplate;          // this is instance from appConfig  as we mentioned @Bean in restTemplate
    JobRepository jobRepository;
    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private ReviewClient reviewClient;

    int attempts=0;
    @Override
    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt : "+ (++attempts));
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> JobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    private JobDTO convertToDTO(Job job){

        JobDTO JobDTO = new JobDTO();
        Company company = companyClient.getCompany(job.getId());
        List<Review> reviews = reviewClient.getReviews(company.getId());
        JobDTO = JobMapper.mapToJobWithCompanyDto(job,company, reviews);
        return JobDTO;

    }

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO findJob(long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        return convertToDTO(job);
    }

    @Override
    public boolean deleteJob(long jobId) {
        try{
            Optional<Job> jobOptional = jobRepository.findById(jobId);
            if(jobOptional.isPresent()){
                jobRepository.deleteById(jobId);
                return true;
            }
            return false;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean updateJob(long jobId, Job updatedJob) {
        System.out.println(updatedJob);
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if(jobOptional.isPresent())     {
            Job jobToUpdate = jobOptional.get();
            jobToUpdate.setTitle(updatedJob.getTitle());
            jobToUpdate.setDescription(updatedJob.getDescription());
            jobToUpdate.setMinSalary(updatedJob.getMinSalary());
            jobToUpdate.setMaxSalary(updatedJob.getMaxSalary());
            jobToUpdate.setLocation(updatedJob.getLocation());
            jobRepository.save(jobToUpdate);
            return true;
        }
        return false;
    }
}
