package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.request.AuthRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.AuthResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.CreateAndEditUserResponse;
import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
import com.crocusoft.teamprojecttracker.exception.RolePermissionException;
import com.crocusoft.teamprojecttracker.exception.TeamNotFoundException;
import com.crocusoft.teamprojecttracker.exception.UnauthorizedException;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.model.Role;
import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.repository.ProjectRepository;
import com.crocusoft.teamprojecttracker.repository.RoleRepository;
import com.crocusoft.teamprojecttracker.repository.TeamRepository;
import com.crocusoft.teamprojecttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Convert convert;

    public CreateAndEditUserResponse register(UserRequest userRequest) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("SUPER_ADMIN")
                    || role.getAuthority().equals("ADMIN"))) {
                Set<Role> roleList = roleRepository.findAllByNameIn(userRequest.getRoles());
                if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN")
                        && userRequest.getRoles().contains("SUPER_ADMIN"))) {
                    log.error("Attempted to create a new SUPER ADMIN role by an ADMIN user");
                    throw new RolePermissionException("ADMIN role does not have permission to create a new SUPER ADMIN role");
                }
                Team team = teamRepository.findById(userRequest.getTeamId()).orElseThrow(
                        () -> new TeamNotFoundException("Team not found" + userRequest.getTeamId()));

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
    }

    public AuthResponse login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.email(), authRequest.password()));
            if (authentication.isAuthenticated()) {
                User authUser = userRepository.findByEmail(authRequest.email()).orElseThrow(
                        () -> new UnauthorizedException("You have entered an email or password that does not exist."));
                String token = jwtService.createToken(authUser);
                String refreshToken = jwtService.createRefreshToken(authUser);

                return AuthResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error occurred during user login");
            throw new UnauthorizedException("You have entered an email or password that does not exist.");
        }
        throw new UnauthorizedException("Authentication failed.");
    }
}