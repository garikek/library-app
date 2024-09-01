package com.example.BookService.service.implementation;

import com.example.BookService.dto.BookDTO;
import com.example.BookService.dto.BookListDTO;
import com.example.BookService.dto.LibraryRequest;
import com.example.BookService.model.Book;
import com.example.BookService.repository.BookRepository;
import com.example.BookService.service.BookService;
import com.example.BookService.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.stream.Collectors;

import static com.example.BookService.utility.Constant.BOOK_NOT_FOUND_BY_ID;
import static com.example.BookService.utility.Constant.BOOK_NOT_FOUND_BY_ISBN;

@RequiredArgsConstructor
@Service
public class DefaultBookService implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Override
    public BookListDTO getBooks() {
        return new BookListDTO(bookRepository.findAll().stream()
                .map((book) -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList()));
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        Book savedBook = bookRepository.save(book);

//        LibraryRequest libraryRequest = new LibraryRequest();
//        libraryRequest.setBookId(savedBook.getId());
//        libraryRequest.setDateBorrowed(null);
//        libraryRequest.setDateToReturn(null);
//
//        String libraryServiceUrl = "http://localhost:8083";
//        restTemplate.postForEntity(libraryServiceUrl + "/library", libraryRequest, String.class);

        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public BookDTO getBookByIsbn(String isbn) throws BookNotFoundException {
        Book optBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ISBN, isbn)));
        return modelMapper.map(optBook, BookDTO.class);
    }

    @Override
    public BookDTO getBookById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id)));
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public void deleteBookById(Long id) throws BookNotFoundException {
        if(!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO book) throws BookNotFoundException {
        Book optBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id)));
        optBook.setAuthor(book.getAuthor());
        optBook.setTitle(book.getTitle());
        optBook.setGenre(book.getGenre());
        optBook.setIsbn(book.getIsbn());
        optBook.setDescription(book.getDescription());
        bookRepository.save(optBook);
        return modelMapper.map(optBook, BookDTO.class);
    }

}