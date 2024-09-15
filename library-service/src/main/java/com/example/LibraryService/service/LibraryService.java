package com.example.libraryservice.service;

import com.example.libraryservice.dto.LibraryDTORequest;
import com.example.libraryservice.dto.LibraryDTOResponse;
import com.example.libraryservice.dto.LibraryListDTO;

public interface LibraryService {
    LibraryListDTO getFreeBooks();

    LibraryDTOResponse updateBook(Long id, LibraryDTORequest libraryDTORequest);

    LibraryDTOResponse addBook(LibraryDTORequest libraryDTORequest);
}