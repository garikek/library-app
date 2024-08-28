package com.example.AuthenticationService.controller;

import com.example.AuthenticationService.dto.JWTAuthRequest;
import com.example.AuthenticationService.dto.JWTAuthResponse;
import com.example.AuthenticationService.dto.UserDTO;
import com.example.AuthenticationService.exception.UserNotFoundException;
import com.example.AuthenticationService.exception.WrongPasswordException;
import com.example.AuthenticationService.service.implementation.DefaultAuthenticationService;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private DefaultAuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody JWTAuthRequest authRequest) throws UserNotFoundException, WrongPasswordException {
        return ResponseEntity.ok(authenticationService.login(authRequest));
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void register(@RequestBody UserDTO userDTO) throws AuthException {
        authenticationService.register(userDTO);
    }

}