package com.example.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTOResponse {
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;
}
