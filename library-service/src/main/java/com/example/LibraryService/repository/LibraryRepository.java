package com.example.libraryservice.repository;

import com.example.libraryservice.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> findByDateToReturnBefore(LocalDate date);

}