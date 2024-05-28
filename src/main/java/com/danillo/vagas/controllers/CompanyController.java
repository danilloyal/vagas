package com.danillo.vagas.controllers;

import com.danillo.vagas.models.company.Company;
import com.danillo.vagas.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/")
    public ResponseEntity<Company> create(@RequestBody Company company){
        Company newCompany = this.companyService.createCompany(company);
        return ResponseEntity.ok().body(newCompany);
    }
}
