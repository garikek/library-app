package com.example.LibraryService.repository;

import com.example.LibraryService.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    List<Library> findByDateBorrowedIsNull();
}