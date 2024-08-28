package com.example.LibraryService.service.implementation;

import com.example.LibraryService.dto.LibraryDTO;
import com.example.LibraryService.dto.LibraryListDTO;
import com.example.LibraryService.exception.LibraryNotFoundException;
import com.example.LibraryService.model.Library;
import com.example.LibraryService.repository.LibraryRepository;
import com.example.LibraryService.service.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

import static com.example.LibraryService.utility.Constant.LIBRARY_NOT_FOUND_BY_ID;

@RequiredArgsConstructor
@Service
public class DefaultLibraryService implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;



    public LibraryListDTO getFreeBooks() {
        return new LibraryListDTO(libraryRepository.findByDateBorrowedIsNull().
                stream().map((book) -> modelMapper.map(book, LibraryDTO.class)).
                collect(Collectors.toList()));
    }

    @Override
    public LibraryDTO updateBook(Long id, LibraryDTO libraryDTO) throws LibraryNotFoundException {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException(String.format(LIBRARY_NOT_FOUND_BY_ID, id)));
        library.setBookId(libraryDTO.getBookId());
        library.setDateBorrowed(libraryDTO.getDateBorrowed());
        library.setDateToReturn(libraryDTO.getDateBorrowed());
        libraryRepository.save(library);
        return modelMapper.map(library, LibraryDTO.class);
    }

    @Override
    public LibraryDTO addBook(LibraryDTO libraryDTO) {
        Library library = modelMapper.map(libraryDTO, Library.class);
        Library savedLibrary = libraryRepository.save(library);
        return modelMapper.map(savedLibrary, LibraryDTO.class);
    }
}