package com.danillo.vagas.repositories;

import com.danillo.vagas.models.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByUsernameOrEmail(String username, String email);

    Optional<Candidate> findByUsername (String username);
}