package com.example.authenticationservice.service;

import com.example.authenticationservice.dto.JWTAuthRequest;
import com.example.authenticationservice.dto.JWTAuthResponse;
import com.example.authenticationservice.dto.UserDTO;
import com.example.authenticationservice.exception.RegistrationException;
import com.example.authenticationservice.exception.UserNotFoundException;
import com.example.authenticationservice.exception.WrongPasswordException;
import lombok.NonNull;

public interface AuthenticationService {
    JWTAuthResponse login(@NonNull JWTAuthRequest authRequest) throws UserNotFoundException, WrongPasswordException;
    UserDTO register(UserDTO userDTO) throws RegistrationException;


}