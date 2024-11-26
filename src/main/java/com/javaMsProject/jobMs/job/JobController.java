package com.javaMsProject.jobMs.job;

import com.javaMsProject.jobMs.job.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class JobController {
    @Autowired
    private JobService jobService;
     @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> findAll(){
         return ResponseEntity.ok(jobService.findAll());
    }
    @PostMapping("/jobs")
    public  ResponseEntity<String> createJob(@RequestBody Job job){
         jobService.createJob(job);
        return new ResponseEntity<>("Job added successfully",HttpStatus.OK);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> findJobById(@PathVariable long id) {
        JobDTO findJobById = jobService.findJob(id);
        if (findJobById != null) return new ResponseEntity<>(findJobById, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable long id) {
        boolean isJobDeleted = jobService.deleteJob(id);
        if(isJobDeleted) return new ResponseEntity<>("Job deleted successfully",HttpStatus.OK);
        return new ResponseEntity<>("Job Not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/jobs/{id}")  // Update job based on jobId
    public ResponseEntity<Job> updateJob(@PathVariable long id, @RequestBody Job job) {
        System.out.println(job+"inside controller");
        boolean isJobUpdated = jobService.updateJob(id, job);
        if(isJobUpdated)  return new ResponseEntity<>( HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
