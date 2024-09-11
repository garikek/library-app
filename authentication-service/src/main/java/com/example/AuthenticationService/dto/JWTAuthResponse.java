package com.example.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String refreshToken;
}