package com.example.libraryservice.service;

import com.example.libraryservice.dto.LibraryDTO;
import com.example.libraryservice.dto.LibraryListDTO;
import com.example.libraryservice.exception.LibraryNotFoundException;

public interface LibraryService {
    LibraryListDTO getFreeBooks();

    LibraryDTO updateBook(Long id, LibraryDTO libraryDTO);

    LibraryDTO addBook(LibraryDTO libraryDTO);
}