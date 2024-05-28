package com.danillo.vagas.controllers;

import com.danillo.vagas.dto.JobDTO;
import com.danillo.vagas.models.job.Job;
import com.danillo.vagas.services.JobService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Job> createJob(@Valid @RequestBody JobDTO jobDTO, HttpServletRequest request){
        var companyId = request.getAttribute("companyId");

        var job = Job.builder()
                .description(jobDTO.description())
                .benefits(jobDTO.benefits())
                .companyId(Long.parseLong(companyId.toString()))
                .level(jobDTO.level())
                .build();

        Job newJob = this.jobService.create(job);
        return ResponseEntity.ok().body(newJob);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<Job>> findAllJobsByFilter(@RequestParam String filter){
        var result = this.jobService.listAllJobsByFilter(filter);
        return ResponseEntity.ok().body(result);
    }
}
