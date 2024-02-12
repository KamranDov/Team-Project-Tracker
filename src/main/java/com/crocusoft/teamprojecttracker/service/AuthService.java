package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.request.AuthRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.AuthResponse;
import com.crocusoft.teamprojecttracker.dto.response.UserResponse;
import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
import com.crocusoft.teamprojecttracker.exception.RolePermissionException;
import com.crocusoft.teamprojecttracker.exception.UserRegistrationException;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.mapper.ModelMapper;
import com.crocusoft.teamprojecttracker.model.Role;
import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.repository.RoleRepository;
import com.crocusoft.teamprojecttracker.repository.TeamRepository;
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

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final Convert convert;


    public UserResponse register(UserRequest userRequest) throws RolePermissionException, UserRegistrationException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Team team = teamRepository.findByName(userRequest.getTeamName());
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                Set<Role> roleList = roleRepository.findAllByNameIn(userRequest.getRoles());
                if (userRequest.getRoles().contains("SUPER_ADMIN")) {
                    log.error("Attempted to create a new SUPER ADMIN role by an ADMIN user");
                    throw new RolePermissionException("ADMIN role does not have permission to create a new SUPER ADMIN role");
                }
                User newUser = User.builder()
                        .name(userRequest.getName())
                        .surname(userRequest.getSurname())
                        .password(passwordEncoder.encode(userRequest.getPassword()))
                        .email(userRequest.getEmail())
                        .userActionStatus(UserActionStatus.ACTIVE)
                        .authorities(roleList)
                        .team(team)
                        .build();
                userRepository.save(newUser);
                return this.convert.userToUserResponse(newUser);
            } else {
                log.error("Unauthorized attempt to register a new user by a non-ADMIN user");
                throw new RolePermissionException("No permission");
            }
        } catch (Exception e) {
            log.error("Error occurred during user registration: {}", e.getMessage());
            throw new UserRegistrationException("An error occurred during user registration.");
        }
    }

    public AuthResponse login(AuthRequest authRequest) {
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

