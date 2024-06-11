package com.danillo.vagas.controllers;

import com.danillo.vagas.TestUtils;
import com.danillo.vagas.models.company.Company;
import com.danillo.vagas.models.company.exceptions.CompanyNotFoundException;
import com.danillo.vagas.models.job.Job;
import com.danillo.vagas.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static com.danillo.vagas.TestUtils.objectToJSON;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class JobControllerTest {
    private MockMvc mvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebApplicationContext context;

    private final Random random = new Random();

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var newComp = Company.builder()
                .description("COMPANY_DESCRIPTION_TEST")
                .email("EMAIL@COMPANY.COM")
                .password("1234")
                .username("COMPANY_USERNAME_TEST")
                .name("COMPANY_NAME_TEST").build();

        newComp  = companyRepository.saveAndFlush(newComp);

        var newJob = Job.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJSON(newJob))
                        .header("Authorization", TestUtils.generateToken(newComp.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
        var random_long = Math.abs(random.nextLong()) + 1;

        var newJob = Job.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJSON(newJob))
                        .header("Authorization", TestUtils.generateToken(random_long)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException));
    }
}