package com.danillo.vagas.models.candidate;

import com.danillo.vagas.models.job.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private Candidate candidate;
    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;
    @Column(name = "candidate_id")
    private Long candidateId;
    @Column(name = "job_id")
    private Long jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
