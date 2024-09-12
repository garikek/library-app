package com.example.libraryservice.controller;

import com.example.libraryservice.dto.LibraryDTO;
import com.example.libraryservice.dto.LibraryListDTO;
import com.example.libraryservice.exception.LibraryNotFoundException;
import com.example.libraryservice.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/library")
@Tag(name = "Library", description = "Endpoints for managing library books")
public class LibraryController {
    private final LibraryService libraryService;

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
    @PostMapping
    public ResponseEntity<LibraryDTO> addBookToLibrary(@RequestBody LibraryDTO libraryDTO) {
        LibraryDTO createdLibraryDTO = libraryService.addBook(libraryDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLibraryDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdLibraryDTO);
    }
}