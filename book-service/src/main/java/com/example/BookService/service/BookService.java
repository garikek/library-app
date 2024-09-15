package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTORequest;
import com.example.bookservice.dto.BookDTOResponse;
import com.example.bookservice.dto.BookListDTO;

public interface BookService {
    BookListDTO getBooks();

    BookDTOResponse addBook(BookDTORequest bookDTO);

    BookDTOResponse getBookByIsbn(String isbn);

    BookDTOResponse getBookById(Long id);

    void deleteBookById(Long id);

    BookDTOResponse updateBook(Long id, BookDTORequest bookDTO);
}