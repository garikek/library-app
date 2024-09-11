package com.example.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String s) {
        super(s);
    }
}