package com.danillo.vagas;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static String objectToJSON(Object o) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateToken(Long idCompany){
        Algorithm algorithm = Algorithm.HMAC256("SECRET_KEY");
        var token = JWT.create().withIssuer("vagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(idCompany.toString())
                .withClaim("roles", List.of("COMPANY"))
                .sign(algorithm);
        return token;
    }
}
