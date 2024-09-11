package com.example.authenticationservice.controller;

import com.example.authenticationservice.dto.JWTAuthRequest;
import com.example.authenticationservice.dto.JWTAuthResponse;
import com.example.authenticationservice.dto.UserDTO;
import com.example.authenticationservice.exception.RegistrationException;
import com.example.authenticationservice.exception.UserNotFoundException;
import com.example.authenticationservice.exception.WrongPasswordException;
import com.example.authenticationservice.service.implementation.DefaultAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {
    private DefaultAuthenticationService authenticationService;

    @Operation(summary = "Login", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody JWTAuthRequest authRequest) throws UserNotFoundException, WrongPasswordException {
        return ResponseEntity.ok(authenticationService.login(authRequest));
    }

    @Operation(summary = "Register", description = "Register a new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws RegistrationException {
        UserDTO createdUser = authenticationService.register(userDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

}