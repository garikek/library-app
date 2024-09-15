package com.example.bookservice.service.implementation;

import com.example.bookservice.dto.BookDTORequest;
import com.example.bookservice.dto.BookDTOResponse;
import com.example.bookservice.dto.BookListDTO;
import com.example.bookservice.dto.LibraryDTORequest;
import com.example.bookservice.exception.DuplicateIsbnException;
import com.example.bookservice.exception.InvalidIsbnException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookService;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.utility.IsbnValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.bookservice.utility.Constant.BOOK_NOT_FOUND_BY_ID;
import static com.example.bookservice.utility.Constant.BOOK_NOT_FOUND_BY_ISBN;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultBookService implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Override
    public BookListDTO getBooks() {
        return new BookListDTO(bookRepository.findAll().stream()
                .map((book) -> modelMapper.map(book, BookDTOResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public BookDTOResponse addBook(BookDTORequest bookDTORequest) {
        log.info("Starting the process of adding a new book: {}", bookDTORequest);

        Book book = modelMapper.map(bookDTORequest, Book.class);

        if (!IsbnValidator.isValidIsbn(book.getIsbn())) {
            throw new InvalidIsbnException(String.format("Invalid ISBN: %s", book.getIsbn()));
        }

        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(book.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(book.getId())) {
            throw new DuplicateIsbnException(String.format("ISBN %s exists already.", book.getIsbn()));
        }

        Book savedBook = bookRepository.save(book);

        log.info("Book saved in the database with ID: {}", savedBook.getId());

        LibraryDTORequest libraryDTORequest = new LibraryDTORequest();
        libraryDTORequest.setBookId(savedBook.getId());
        libraryDTORequest.setDateBorrowed(LocalDate.now());
        libraryDTORequest.setDateToReturn(LocalDate.now().plusDays(14));

        try {
            URI location = restTemplate.postForLocation("http://library-service/api/v1/library", libraryDTORequest);
            log.info("Book successfully sent to LibraryService, location: {}", location);
        } catch (Exception e) {
            log.error("Failed to send book to LibraryService. Error: {}", e.getMessage());
        }

        return modelMapper.map(savedBook, BookDTOResponse.class);
    }

    @Override
    public BookDTOResponse getBookByIsbn(String isbn) {
        Book optBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ISBN, isbn)));
        return modelMapper.map(optBook, BookDTOResponse.class);
    }

    @Override
    public BookDTOResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id)));
        return modelMapper.map(book, BookDTOResponse.class);
    }

    @Override
    public void deleteBookById(Long id) {
        if(!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTOResponse updateBook(Long id, BookDTORequest book) {
        Book optBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID, id)));

        if (!IsbnValidator.isValidIsbn((book.getIsbn()))) {
            throw new InvalidIsbnException(String.format("Invalid ISBN: %s", book.getIsbn()));
        }

        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(book.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(id)) {
            throw new DuplicateIsbnException(String.format("ISBN %s exists already.", book.getIsbn()));
        }

        optBook.setAuthor(book.getAuthor());
        optBook.setTitle(book.getTitle());
        optBook.setGenre(book.getGenre());
        optBook.setIsbn(book.getIsbn());
        optBook.setDescription(book.getDescription());
        bookRepository.save(optBook);
        return modelMapper.map(optBook, BookDTOResponse.class);
    }

}