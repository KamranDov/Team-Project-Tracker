package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.common.OTPGenerator;
import com.crocusoft.teamprojecttracker.dto.EmailDto;
import com.crocusoft.teamprojecttracker.dto.ForgotPasswordDto;
import com.crocusoft.teamprojecttracker.dto.request.ChangePasswordRequest;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.user.CreateAndEditUserResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.FilterUserResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.UserFilterDto;
import com.crocusoft.teamprojecttracker.dto.response.user.ViewUserResponse;
import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
import com.crocusoft.teamprojecttracker.exception.PasswordMismatchException;
import com.crocusoft.teamprojecttracker.exception.RoleNotFoundException;
import com.crocusoft.teamprojecttracker.exception.TeamNotFoundException;
import com.crocusoft.teamprojecttracker.exception.UserNotFoundException;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.model.*;
import com.crocusoft.teamprojecttracker.repository.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class UserService {

    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final DailyReportRepository dailyReportRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Convert convert;
    private final OTPGenerator otpGenerator;


    public CreateAndEditUserResponse editUser(Long id, UserRequest userRequest, UserActionStatus userActionStatus) {
        Optional<User> optionalTeam = userRepository.findById(id);
        if (optionalTeam.isPresent()) {
            User editUser = optionalTeam.get();

            Team team = teamRepository.findById(userRequest.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
            if (team == null) {
                log.error("Team not found with the name: {}", userRequest.getTeamId());
                throw new TeamNotFoundException("Team not found with the given name");
            }
            Set<Role> roleList = roleRepository.findAllByNameIn(userRequest.getRoles());
            if (roleList.isEmpty()) {
                log.error("No roles found for the given role names: {}", userRequest.getRoles());
                throw new RoleNotFoundException("No roles found for the given role names");
            }
            editUser.setName(userRequest.getName());
            editUser.setSurname(userRequest.getSurname());
            editUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            editUser.setEmail(userRequest.getEmail());
            editUser.setUserActionStatus(userActionStatus);
            editUser.setAuthorities(roleList);
            editUser.setTeam(team);

            userRepository.save(editUser);
            return this.convert.userToUserResponse(editUser);
        } else {
            log.error("User not found with the id: {}", id);
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    public ViewUserResponse viewUserById(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ViewUserResponse viewUserResponse = convert.userToUserViewResponse(user);
            return viewUserResponse;
        } else throw new UserNotFoundException("User not found with ID: " + id);
    }

    public FilterUserResponse filterUser(Integer pageNumber,
                                         Integer pageCount,
                                         String name,
                                         String surname,
                                         List<Long> teamId,
                                         List<Long> projectId) {

        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, pageCount));

        List<User> filteredUsers = userPage.getContent()
                .stream()
                .filter(user -> (name == null || user.getName().contains(name)) &&
                        (surname == null || user.getSurname().contains(surname)) &&
                        (teamId == null || teamId.isEmpty() || (user.getTeam() != null &&
                                teamId.contains(user.getTeam().getId()))) &&
                        (projectId == null || projectId.isEmpty() || (user.getProjects() != null &&
                                user.getProjects().stream().map(Project::getId).anyMatch(projectId::contains))))
                .toList();

        List<UserFilterDto> filterDto = filteredUsers.stream()
                .map(user -> new UserFilterDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()),
                        user.getTeam().getId(),
                        user.getProjects().stream().map(Project::getId).toList())
                )
                .collect(Collectors.toList());

        return new FilterUserResponse(filterDto, userPage.getTotalPages(), userPage.getTotalElements(), userPage.hasNext());
    }

    @CachePut(value = "forgotPassword", key = "#otp")
    public ForgotPasswordDto verifyMail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Please provide an valid email!"));

        String generatedOTP = otpGenerator.generateOTP();
        EmailDto emailDto = EmailDto.builder()
                .email(email)
                .subject("OTP for forgot password request")
                .message("Attention! This code is confidential. Do not share this code with anyone.\n" + "OTP code: "
                        + generatedOTP
                        + ".")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(generatedOTP)
                .lifeTime(new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis()))
                .user(user)
                .build();

        ForgotPasswordDto forgotPasswordDto = ForgotPasswordDto.builder()
                .otp(forgotPassword.getOtp())
                .lifeTime(forgotPassword.getLifeTime())
                .userId(forgotPassword.getUser().getId())
                .build();

        emailService.sendMail(emailDto);
        forgotPasswordRepository.save(forgotPassword);
        return forgotPasswordDto;
    }

    @Cacheable(value = "forgotPassword", key = "#otp")
    public ForgotPasswordDto verifyOtp(String otp, String email) {

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUserEmail(otp, email)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + otp));

        ForgotPasswordDto forgotPasswordConvert = ForgotPasswordDto.builder()
                .otp(forgotPassword.getOtp())
                .lifeTime(forgotPassword.getLifeTime())
                .userId(forgotPassword.getUser().getId())
                .build();

        if (forgotPassword.getLifeTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            throw new RuntimeException("Otp verification failed. The OTP has expired.");
        }
        return forgotPasswordConvert;
    }

    public String changePassword(ChangePasswordRequest passwordRequest, String email) {
        if (!Objects.equals(passwordRequest.getPassword(), passwordRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Please enter the password again!");
        }
        String encodedPassword = passwordEncoder.encode(passwordRequest.getPassword());
        userRepository.updatePassword(email, encodedPassword);
        return "Password has been changed!";
    }
}