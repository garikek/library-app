package com.example.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RegistrationException extends RuntimeException {
    public RegistrationException(String s) {
        super(s);
    }
}