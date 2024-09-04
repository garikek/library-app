package com.example.BookService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIsbnException extends RuntimeException{
    public InvalidIsbnException(String s) {
        super(s);
    }
}
