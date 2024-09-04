package com.example.AuthenticationService.config;

import com.example.AuthenticationService.exception.UserNotFoundException;
import com.example.AuthenticationService.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = "An unexpected error occurred. Please try again later.";
        ex.printStackTrace();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
