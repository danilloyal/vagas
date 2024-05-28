package com.danillo.vagas.controllers;

import com.danillo.vagas.models.candidate.Candidate;
import com.danillo.vagas.models.candidate.exceptions.UserAlreadyExistsException;
import com.danillo.vagas.repositories.CandidateRepository;
import com.danillo.vagas.services.CandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/")
    public Candidate create(@Valid @RequestBody Candidate candidate) {
        return this.candidateService.createCandidate(candidate);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> getCandidate(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.candidateService.profileCandidate(Long.parseLong(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
