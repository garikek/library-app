package com.example.libraryservice.controller;

import com.example.libraryservice.dto.LibraryDTORequest;
import com.example.libraryservice.dto.LibraryDTOResponse;
import com.example.libraryservice.dto.LibraryListDTO;
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
    public ResponseEntity<LibraryDTOResponse> updateBook(@PathVariable Long id, @RequestBody LibraryDTORequest libraryDTORequest) {
        LibraryDTOResponse updatedLibraryDTOResponse = libraryService.updateBook(id, libraryDTORequest);
        return ResponseEntity.ok().body(updatedLibraryDTOResponse);
    }

    @Operation(summary = "Add a new book", description = "Add a new book to the library")
    @PostMapping
    public ResponseEntity<LibraryDTOResponse> addBookToLibrary(@RequestBody LibraryDTORequest libraryDTORequest) {
        LibraryDTOResponse createdLibraryDTOResponse = libraryService.addBook(libraryDTORequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLibraryDTOResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdLibraryDTOResponse);
    }
}