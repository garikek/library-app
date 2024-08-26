package com.example.AuthenticationService.service;

import com.example.AuthenticationService.dto.JWTAuthRequest;
import com.example.AuthenticationService.dto.JWTAuthResponse;
import com.example.AuthenticationService.dto.UserDTO;
import com.example.AuthenticationService.exception.UserNotFoundException;
import com.example.AuthenticationService.exception.WrongPasswordException;
import com.example.AuthenticationService.model.UserCredential;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import org.apache.catalina.User;

public interface AuthenticationService {
    JWTAuthResponse login(@NonNull JWTAuthRequest authRequest) throws UserNotFoundException, WrongPasswordException;
    void register(UserDTO userDTO) throws AuthException;


}