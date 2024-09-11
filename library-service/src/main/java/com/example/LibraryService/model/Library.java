package com.example.libraryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(bookId, library.bookId) &&
                Objects.equals(dateBorrowed, library.dateBorrowed) &&
                Objects.equals(dateToReturn, library.dateToReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, dateBorrowed, dateToReturn);
    }

    @Override
    public String toString() {
        return "Library{" +
                "bookId=" + bookId +
                ", dateBorrowed=" + dateBorrowed +
                ", dateToReturn=" + dateToReturn +
                '}';
    }
}