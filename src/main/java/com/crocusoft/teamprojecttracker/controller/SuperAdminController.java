package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.UserResponse;
import com.crocusoft.teamprojecttracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/super-admin")
public class SuperAdminController {

    private final AuthService authService;

//    @PostMapping("/sign-up")
//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
//    ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
//return new ResponseEntity<>(authService.register(userRequest),CREATED);
//    }

}
