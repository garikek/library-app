package com.example.authenticationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOResponse {
    private Long id;
    private String userName;
    private String email;
    private String password;
}