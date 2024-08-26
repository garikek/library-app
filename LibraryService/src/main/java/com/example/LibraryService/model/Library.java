package com.example.LibraryService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "library")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "borrow_at")
    private LocalDate dateBorrowed;
    @Column(name = "return_before")
    private LocalDate dateToReturn;
}