package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.ForgotPasswordDto;
import com.crocusoft.teamprojecttracker.dto.request.AuthRequest;
import com.crocusoft.teamprojecttracker.dto.request.ChangePasswordRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.AuthResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.CreateAndEditUserResponse;
import com.crocusoft.teamprojecttracker.exception.UnauthorizedException;
import com.crocusoft.teamprojecttracker.exception.UserRegistrationException;
import com.crocusoft.teamprojecttracker.model.ForgotPassword;
import com.crocusoft.teamprojecttracker.service.AuthService;
import com.crocusoft.teamprojecttracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.login(authRequest), OK);
    }

    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
//    @RolesAllowed({"ADMIN", "SUPER_ADMIN"})
    ResponseEntity<CreateAndEditUserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(authService.register(userRequest), CREATED);
    }

    @PostMapping("/verify{email}")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<ForgotPasswordDto> verifyEmail(@PathVariable(value = "email") String email) {
        return new ResponseEntity<>(userService.verifyMail(email), OK);
    }

    @PostMapping("/verifyOTP/{otp}/{email}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ForgotPasswordDto> verifyOtp(@PathVariable(value = "otp") String otp,
                                                       @PathVariable(value = "email") String email) {
        return new ResponseEntity<>(userService.verifyOtp(otp, email), OK);
    }

    @PostMapping("/change-password/{email}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest passwordRequest,
                                                 @PathVariable(value = "email") String email) {
        return new ResponseEntity<>(userService.changePassword(passwordRequest, email), OK);
    }
}