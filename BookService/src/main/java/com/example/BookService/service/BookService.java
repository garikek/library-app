package com.example.BookService.service;

import com.example.BookService.dto.BookDTO;
import com.example.BookService.dto.BookListDTO;
import com.example.BookService.exception.BookNotFoundException;

public interface BookService {
    BookListDTO getBooks();

    BookDTO addBook(BookDTO bookDTO);

    BookDTO getBookByIsbn(String isbn) throws BookNotFoundException;

    BookDTO getBookById(Long id) throws BookNotFoundException;

    void deleteBookById(Long id) throws BookNotFoundException;

    BookDTO updateBook(Long id, BookDTO bookDTO) throws BookNotFoundException;
}