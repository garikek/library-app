package com.example.libraryservice.service.implementation;

import com.example.libraryservice.dto.LibraryDTORequest;
import com.example.libraryservice.dto.LibraryDTOResponse;
import com.example.libraryservice.dto.LibraryListDTO;
import com.example.libraryservice.exception.LibraryNotFoundException;
import com.example.libraryservice.model.Library;
import com.example.libraryservice.repository.LibraryRepository;
import com.example.libraryservice.service.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.libraryservice.utility.Constant.LIBRARY_NOT_FOUND_BY_ID;

@RequiredArgsConstructor
@Service
public class DefaultLibraryService implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public LibraryListDTO getFreeBooks() {
        List<Library> freeBooks = libraryRepository.findByDateToReturnIsNull();
        return new LibraryListDTO(freeBooks.stream()
                .map(book -> modelMapper.map(book, LibraryDTOResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public LibraryDTOResponse updateBook(Long id, LibraryDTORequest libraryDTORequest) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException(String.format(LIBRARY_NOT_FOUND_BY_ID, id)));
        library.setBookId(libraryDTORequest.getBookId());
        library.setDateBorrowed(libraryDTORequest.getDateBorrowed());
        library.setDateToReturn(libraryDTORequest.getDateToReturn());
        libraryRepository.save(library);
        return modelMapper.map(library, LibraryDTOResponse.class);
    }

    @Override
    public LibraryDTOResponse addBook(LibraryDTORequest libraryDTORequest) {
        Library library = modelMapper.map(libraryDTORequest, Library.class);
        Library savedLibrary = libraryRepository.save(library);
        return modelMapper.map(savedLibrary, LibraryDTOResponse.class);
    }
}