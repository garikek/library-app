package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.BookListDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.exception.DuplicateIsbnException;
import com.example.bookservice.exception.InvalidIsbnException;

public interface BookService {
    BookListDTO getBooks();

    BookDTO addBook(BookDTO bookDTO);

    BookDTO getBookByIsbn(String isbn);

    BookDTO getBookById(Long id);

    void deleteBookById(Long id);

    BookDTO updateBook(Long id, BookDTO bookDTO);
}