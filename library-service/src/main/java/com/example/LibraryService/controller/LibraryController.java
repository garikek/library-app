package com.example.LibraryService.controller;

import com.example.LibraryService.dto.LibraryDTO;
import com.example.LibraryService.dto.LibraryListDTO;
import com.example.LibraryService.exception.LibraryNotFoundException;
import com.example.LibraryService.service.implementation.DefaultLibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/library")
@Tag(name = "Library", description = "Endpoints for managing library books")
public class LibraryController {
    private final DefaultLibraryService libraryService;

    @Operation(summary = "Get free books", description = "Retrieve a list of books that are currently not borrowed")
    @GetMapping("/free")
    public LibraryListDTO getFreeBooks() {
        return libraryService.getFreeBooks();
    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book in the library")
    @PutMapping("/{id}")
    public ResponseEntity<LibraryDTO> updateBook(@PathVariable Long id, @RequestBody LibraryDTO libraryDTO) throws LibraryNotFoundException {
        LibraryDTO updatedLibraryDTO = libraryService.updateBook(id, libraryDTO);
        return ResponseEntity.ok().body(updatedLibraryDTO);
    }

    @Operation(summary = "Add a new book", description = "Add a new book to the library")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBookToLibrary(@RequestBody LibraryDTO libraryDTO) {
        libraryService.addBook(libraryDTO);
    }
}