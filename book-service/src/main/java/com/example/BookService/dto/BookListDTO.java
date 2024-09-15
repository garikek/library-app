package com.example.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BookListDTO {
    private List<BookDTOResponse> bookDTOList;
}