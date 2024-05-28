package com.danillo.vagas.models.company;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "\\S+",message="Blank spaces not allowed.")
    private String username;
    @Email(message = "Valid e-mail only.")
    private String email;
    private String password;
    private String website;
    private String name;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
