//package com.crocusoft.teamprojecttracker.util;
//
//import com.crocusoft.teamprojecttracker.dto.request.RegisterRequest;
//import com.crocusoft.teamprojecttracker.enums.RoleEnum;
//import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
//import com.crocusoft.teamprojecttracker.model.Role;
//import com.crocusoft.teamprojecttracker.model.User;
//import com.crocusoft.teamprojecttracker.repository.RoleRepository;
//import com.crocusoft.teamprojecttracker.repository.UserRepository;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//@Builder
//public class SuperAdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        this.createSuperAdministrator();
//    }
//
//    private void createSuperAdministrator() {
//        RegisterRequest registerRequest = RegisterRequest.builder()
//                .name("Kamran")
//                .surname("Ahmadov")
//                .password(new BCryptPasswordEncoder().encode("superadmin"))
//                .email("superadmin@crocusoft.com")
//                .userActionStatus(UserActionStatus.ACTIVE)
//                .build();
//
//        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
//        Optional<User> optionalUser = userRepository.findByEmail(registerRequest.email());
//
//        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
//            return;
//        }
//        Role superAdminRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN)
//                .orElseThrow(() -> new RuntimeException("Super Admin role not found"));
//
//        User superAdmin = User.builder()
//                .name(registerRequest.name())
//                .surname(registerRequest.surname())
//                .password(passwordEncoder.encode(registerRequest.password()))
//                .email(registerRequest.email())
//                .userActionStatus(registerRequest.userActionStatus())
//                .roles(superAdminRole)
//                .build();
//
//        userRepository.save(superAdmin);
//    }
//}
