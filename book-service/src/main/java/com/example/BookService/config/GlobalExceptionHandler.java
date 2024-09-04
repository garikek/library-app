package com.example.BookService.config;

import com.example.BookService.exception.BookNotFoundException;
import com.example.BookService.exception.DuplicateIsbnException;
import com.example.BookService.exception.InvalidIsbnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<String> handleDuplicateIsbnException(DuplicateIsbnException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidIsbnException.class)
    public ResponseEntity<String> handleInvalidIsbnException(InvalidIsbnException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = "An unexpected error occurred. Please try again later.";
        ex.printStackTrace();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
