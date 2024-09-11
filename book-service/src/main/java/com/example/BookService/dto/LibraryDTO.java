package com.example.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LibraryDTO {
    private Long id;
    private Long bookId;
    private LocalDate dateBorrowed;
    private LocalDate dateToReturn;
}