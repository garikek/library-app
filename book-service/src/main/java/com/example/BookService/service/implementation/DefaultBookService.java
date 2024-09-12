package com.example.bookservice.service.implementation;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.BookListDTO;
import com.example.bookservice.dto.LibraryDTO;
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
                .map((book) -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList()));
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) throws InvalidIsbnException, DuplicateIsbnException {
        log.info("Starting the process of adding a new book: {}", bookDTO);

        Book book = modelMapper.map(bookDTO, Book.class);

        if (!IsbnValidator.isValidIsbn((book.getIsbn()))) {
            throw new InvalidIsbnException(String.format("Invalid ISBN: %s", book.getIsbn()));
        }

        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(book.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(book.getId())) {
            throw new DuplicateIsbnException(String.format("ISBN %s exists already.", book.getIsbn()));
        }

        Book savedBook = bookRepository.save(book);

        log.info("Book saved in the database with ID: {}", savedBook.getId());

        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setBookId(savedBook.getId());
        libraryDTO.setDateBorrowed(LocalDate.now());
        libraryDTO.setDateToReturn(LocalDate.now().plusDays(14));

        try {
            URI location = restTemplate.postForLocation("http://library-service/api/v1/library", libraryDTO);
            log.info("Book successfully sent to LibraryService, location: {}", location);
        } catch (Exception e) {
            log.error("Failed to send book to LibraryService. Error: {}", e.getMessage());
        }

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
    public BookDTO updateBook(Long id, BookDTO book) throws BookNotFoundException, DuplicateIsbnException, InvalidIsbnException {
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
        return modelMapper.map(optBook, BookDTO.class);
    }

}