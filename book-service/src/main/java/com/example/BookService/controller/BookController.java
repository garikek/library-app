package com.example.bookservice.controller;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.BookListDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.service.implementation.DefaultBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {
    private final DefaultBookService bookService;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @GetMapping
    public BookListDTO getBooks() {
        return bookService.getBooks();
    }

    @Operation(summary = "Add a new book", description = "Add a new book to the database")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBookDTO = bookService.addBook(bookDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBookDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBookDTO);
    }

    @Operation(summary = "Get a book by ISBN", description = "Retrieve a book using its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/isbn/{isbn}")
    public BookDTO getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return bookService.getBookByIsbn(isbn);
    }

    @Operation(summary = "Get a book by ID", description = "Retrieve a book using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the database by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteBookById(id);
    }

    @Operation(summary = "Update a book by ID", description = "Update the details of an existing book by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @RequestBody BookDTO bookDTO) throws BookNotFoundException {
        return ResponseEntity.ok().body(bookService.updateBook(id, bookDTO));
    }
}