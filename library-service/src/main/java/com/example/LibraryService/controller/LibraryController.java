package com.example.LibraryService.controller;

import com.example.LibraryService.dto.LibraryDTO;
import com.example.LibraryService.dto.LibraryListDTO;
import com.example.LibraryService.exception.LibraryNotFoundException;
import com.example.LibraryService.service.implementation.DefaultLibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {
    private final DefaultLibraryService libraryService;

    @GetMapping("/free")
    public ResponseEntity<LibraryListDTO> getFreeBooks() {
        return ResponseEntity.ok().body(libraryService.getFreeBooks());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody LibraryDTO libraryDTO) throws LibraryNotFoundException {
        libraryService.updateBook(id, libraryDTO);
    }

//    for handling new book additions
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBookToLibrary(@RequestBody LibraryDTO libraryDTO) {
        libraryService.addBook(libraryDTO);
    }
}