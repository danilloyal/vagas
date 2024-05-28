package com.danillo.vagas.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.danillo.vagas.dto.AuthCandidateDTO;
import com.danillo.vagas.dto.AuthResponseCandidateDTO;
import com.danillo.vagas.dto.ProfileCandidateDTO;
import com.danillo.vagas.models.candidate.Candidate;
import com.danillo.vagas.models.candidate.exceptions.UserAlreadyExistsException;
import com.danillo.vagas.models.candidate.exceptions.UserNotFoundException;
import com.danillo.vagas.models.company.exceptions.CredentialNotFoundException;
import com.danillo.vagas.models.job.exceptions.JobNotFoundException;
import com.danillo.vagas.repositories.CandidateRepository;
import com.danillo.vagas.repositories.CompanyRepository;
import com.danillo.vagas.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CandidateService {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    public Candidate createCandidate(Candidate candidate) {
        this.candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException("User already exists");
                });
        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);

        return this.candidateRepository.save(candidate);
    }

    public AuthResponseCandidateDTO authenticateCandidate(AuthCandidateDTO authCandidateDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username()).orElseThrow(() -> {
            throw new CredentialNotFoundException("Invalid username or password");
        });

        var pw = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if(!pw)throw new AuthenticationException();

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create()
                .withIssuer("vagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .sign(algorithm);

        return new AuthResponseCandidateDTO(token);
    }

    public ProfileCandidateDTO profileCandidate(Long id){
        var candidate = this.candidateRepository.findById(id).orElseThrow(()->{
            throw new UsernameNotFoundException("User not found");
        });
        ProfileCandidateDTO candidateDTO = new ProfileCandidateDTO();

        BeanUtils.copyProperties(candidate, candidateDTO);

        return candidateDTO;
    }

    public void applyJob(Long idCandidate, Long idJob){
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UserNotFoundException("User not found"));

        this.jobRepository.findById(idJob).orElseThrow(()-> new JobNotFoundException("Job not found"));
    }
}
