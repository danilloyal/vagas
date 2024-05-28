package com.danillo.vagas.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.danillo.vagas.dto.AuthCompanyDTO;
import com.danillo.vagas.dto.AuthResponseCompanyDTO;
import com.danillo.vagas.models.candidate.exceptions.UserAlreadyExistsException;
import com.danillo.vagas.models.company.Company;
import com.danillo.vagas.models.company.exceptions.CredentialNotFoundException;
import com.danillo.vagas.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Value("${security.token.secret}")
    private String secretKey;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public Company createCompany(Company company) {
        this.companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException("User already exists");
                });

        var password = passwordEncoder.encode(company.getPassword());
        company.setPassword(password);

        return this.companyRepository.save(company);
    }

    public AuthResponseCompanyDTO authCompany(AuthCompanyDTO authCompanyDTO){
        var company = this.companyRepository.findByUsername(authCompanyDTO.username()).orElseThrow(() -> {
            throw new CredentialNotFoundException("Invalid username or password.");
        });

        var pwMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());

        if (!pwMatches) {
            throw new CredentialNotFoundException("Invalid username or password.");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("vagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        return new AuthResponseCompanyDTO(token);
    }
}
