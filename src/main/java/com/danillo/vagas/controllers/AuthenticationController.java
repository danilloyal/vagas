package com.danillo.vagas.controllers;

import com.danillo.vagas.dto.AuthCandidateDTO;
import com.danillo.vagas.dto.AuthCompanyDTO;
import com.danillo.vagas.services.CandidateService;
import com.danillo.vagas.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final CompanyService companyService;
    private final CandidateService candidateService;

    @PostMapping("/company/auth")
    public ResponseEntity<Object> authCompany(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            var result = this.companyService.authCompany(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/candidate/auth")
    public ResponseEntity<Object> authCandidate(@RequestBody AuthCandidateDTO authCandidateDTO) {
        try {
            var token = this.candidateService.authenticateCandidate(authCandidateDTO);
            return ResponseEntity.ok().body(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
