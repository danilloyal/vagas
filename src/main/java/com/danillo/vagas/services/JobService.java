package com.danillo.vagas.services;

import com.danillo.vagas.models.company.exceptions.CompanyNotFoundException;
import com.danillo.vagas.models.job.Job;
import com.danillo.vagas.repositories.CompanyRepository;
import com.danillo.vagas.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final CompanyRepository companyRepository;

    public Job create(Job job){

        companyRepository.findById(job.getCompanyId()).orElseThrow(() -> new CompanyNotFoundException("Company Not Found"));
        return this.jobRepository.save(job);
    }

    public List<Job> listAllJobsByFilter(String filter){
        return this.jobRepository.findAllByDescriptionContainingIgnoreCase(filter);
    }
}
