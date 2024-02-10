package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.request.AuthRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.AuthResponse;
import com.crocusoft.teamprojecttracker.dto.response.UserResponse;
import com.crocusoft.teamprojecttracker.exception.RolePermissionException;
import com.crocusoft.teamprojecttracker.exception.UserRegistrationException;
import com.crocusoft.teamprojecttracker.service.AuthService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
//    @RolesAllowed({"ADMIN", "SUPER_ADMIN"})
    ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) throws RolePermissionException, UserRegistrationException {
        return new ResponseEntity<>(authService.register(userRequest), CREATED);

    }
}