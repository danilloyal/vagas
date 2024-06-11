package com.danillo.vagas.repositories;

import com.danillo.vagas.models.candidate.ApplyJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJobRepository extends JpaRepository<ApplyJob, Long> {
}
