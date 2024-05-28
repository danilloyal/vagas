package com.danillo.vagas.repositories;

import com.danillo.vagas.models.candidate.Candidate;
import com.danillo.vagas.models.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUsernameOrEmail(String username, String email);
    Optional<Company> findByUsername(String username);
}
