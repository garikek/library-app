package com.example.authenticationservice.service;

import com.example.authenticationservice.dto.JWTAuthRequest;
import com.example.authenticationservice.dto.JWTAuthResponse;
import com.example.authenticationservice.dto.UserDTORequest;
import com.example.authenticationservice.dto.UserDTOResponse;
import lombok.NonNull;

public interface AuthenticationService {
    JWTAuthResponse login(@NonNull JWTAuthRequest authRequest);
    UserDTOResponse register(UserDTORequest userDTORequest);


}