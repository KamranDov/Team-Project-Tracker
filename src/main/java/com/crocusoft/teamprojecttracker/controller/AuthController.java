package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.request.AuthRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.AuthResponse;
import com.crocusoft.teamprojecttracker.dto.response.UserResponse;
import com.crocusoft.teamprojecttracker.exception.RolePermissionException;
import com.crocusoft.teamprojecttracker.exception.UnauthorizedException;
import com.crocusoft.teamprojecttracker.exception.UserRegistrationException;
import com.crocusoft.teamprojecttracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) throws UnauthorizedException {
        try{
        AuthResponse authResponse = authService.login(authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (UnauthorizedException e){
            String errorMessage = "an error occurred: " + e.getMessage();
            return ResponseEntity.status(UNAUTHORIZED).body(errorMessage);
        }

    }

    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
//    @RolesAllowed({"ADMIN", "SUPER_ADMIN"})
    ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) throws UserRegistrationException {
        return new ResponseEntity<>(authService.register(userRequest), CREATED);

    }
}