package com.danillo.vagas.models.company.exceptions;

public class CredentialNotFoundException extends RuntimeException{
    public CredentialNotFoundException(String message){
        super(message);
    }
}
