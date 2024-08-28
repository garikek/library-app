package com.example.LibraryService.service;

import com.example.LibraryService.dto.LibraryDTO;
import com.example.LibraryService.dto.LibraryListDTO;
import com.example.LibraryService.exception.LibraryNotFoundException;

public interface LibraryService {
    LibraryListDTO getFreeBooks();

    LibraryDTO updateBook(Long id, LibraryDTO libraryDTO) throws LibraryNotFoundException;

    LibraryDTO addBook(LibraryDTO libraryDTO);
}