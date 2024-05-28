package com.danillo.vagas.models.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Pattern(regexp = "\\S+",message="Blank spaces not allowed.")
    private String username;

    @Email(message = "Valid e-mail only.")
    private String email;

    private String password;

    private String description;

    private String curriculum;
}
