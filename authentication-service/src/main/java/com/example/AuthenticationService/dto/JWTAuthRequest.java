package com.example.authenticationservice.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTAuthRequest {
    private String email;
    private String password;
}