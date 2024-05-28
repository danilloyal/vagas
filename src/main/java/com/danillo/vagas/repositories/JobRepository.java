package com.danillo.vagas.repositories;

import com.danillo.vagas.models.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByDescriptionContainingIgnoreCase(String filter);

}
