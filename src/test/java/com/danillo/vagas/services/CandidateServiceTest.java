package com.danillo.vagas.services;

import com.danillo.vagas.models.candidate.ApplyJob;
import com.danillo.vagas.models.candidate.Candidate;
import com.danillo.vagas.models.candidate.exceptions.UserNotFoundException;
import com.danillo.vagas.models.job.Job;
import com.danillo.vagas.models.job.exceptions.JobNotFoundException;
import com.danillo.vagas.repositories.ApplyJobRepository;
import com.danillo.vagas.repositories.CandidateRepository;
import com.danillo.vagas.repositories.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {
    @InjectMocks
    private CandidateService candidateService;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;
    private final Random random = new Random();

    @Test
    void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            candidateService.applyJob(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    void shouldNotBeAbleToApplyJobWithJobNotFound() {
        var idCandidate = Math.abs(random.nextLong()) + 1;
        var candidate = new Candidate();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        try {
            candidateService.applyJob(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    void ShouldBeAbleToApplyJob(){
        var idCandidate = Math.abs(random.nextLong()) + 1;
        var idJob = Math.abs(random.nextLong()) + 1;
        var applyJob = ApplyJob.builder().candidateId(idCandidate).jobId(idJob).build();

        var applyJobCreated = ApplyJob.builder().id(Math.abs(random.nextLong()) + 1).build();
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new Candidate()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new Job()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = candidateService.applyJob(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}