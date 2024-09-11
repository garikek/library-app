package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.BookListDTO;
import com.example.bookservice.exception.BookNotFoundException;

public interface BookService {
    BookListDTO getBooks();

    BookDTO addBook(BookDTO bookDTO);

    BookDTO getBookByIsbn(String isbn) throws BookNotFoundException;

    BookDTO getBookById(Long id) throws BookNotFoundException;

    void deleteBookById(Long id) throws BookNotFoundException;

    BookDTO updateBook(Long id, BookDTO bookDTO) throws BookNotFoundException;
}