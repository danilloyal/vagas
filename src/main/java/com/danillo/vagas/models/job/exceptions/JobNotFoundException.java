package com.danillo.vagas.models.job.exceptions;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException(String message){
        super(message);
    }
}
