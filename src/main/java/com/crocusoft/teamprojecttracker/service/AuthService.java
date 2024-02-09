package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.request.AuthRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.AuthResponse;
import com.crocusoft.teamprojecttracker.dto.response.UserResponse;
import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.mapper.ModelMapper;
import com.crocusoft.teamprojecttracker.model.Role;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.repository.RoleRepository;
import com.crocusoft.teamprojecttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final Convert convert;


    public UserResponse register(UserRequest userRequest) throws Exception {
//        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<Role> roleList = roleRepository.findAllByNameIn(userRequest.roles());
//        Set<Role> allowedRoles = new HashSet<>();
//
//        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {
//            allowedRoles.add(new Role("ADMIN"));
//            allowedRoles.add(new Role("HEAD"));
//            allowedRoles.add(new Role("USER"));
//        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            allowedRoles.add(new Role("HEAD"));
//            allowedRoles.add(new Role("USER"));
//        } else {
//            throw new AccessDeniedException("You don't have permission to perform this action.");
//        }
//
//        if (!new HashSet<>(roleList).containsAll(allowedRoles)) {
//            throw new IllegalArgumentException("Invalid roles specified for user registration.");
//        }
        User newUser = User.builder()
                .name(userRequest.name())
                .surname(userRequest.surname())
                .password(passwordEncoder.encode(userRequest.password()))
                .email(userRequest.email())
                .userActionStatus(UserActionStatus.ACTIVE)
                .authorities(roleList)
                .build();
        userRepository.save(newUser);
        return this.convert.userToUserResponse(newUser);

//        } catch (Exception e) {
//            log.error("Error occurred during user registration: {}", e.getMessage());
//            throw new UserRegistrationException("An error occurred during user registration.");
//        }
    }

    public AuthResponse login(AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.email(), authRequest.password()));

            if (authentication.isAuthenticated()) {
                User authUser = userRepository.findByEmail(authRequest.email()).orElseThrow();

                String token = jwtService.createToken(authUser);
                String refreshToken = jwtService.createRefreshToken(authUser);

                return AuthResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Error occurred during user login: {}", e.getMessage());
            throw new RuntimeException("An error occurred during user login.");
        }
    }
}

