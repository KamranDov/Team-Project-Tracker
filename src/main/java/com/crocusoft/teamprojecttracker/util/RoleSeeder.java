//package com.crocusoft.teamprojecttracker.util;
//
//import com.crocusoft.teamprojecttracker.enums.RoleEnum;
//import com.crocusoft.teamprojecttracker.model.Role;
//import com.crocusoft.teamprojecttracker.repository.RoleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final RoleRepository roleRepository;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        this.loadRoles();
//    }
//
//    private void loadRoles() {
//        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.SUPER_ADMIN, RoleEnum.ADMIN, RoleEnum.HEAD, RoleEnum.USER};
//        Map<RoleEnum, String> roleDescriptionMap = Map.of(
//                RoleEnum.SUPER_ADMIN, "Can create Admin, Head, Employee roles and see all modules.",
//                RoleEnum.ADMIN, "Creates all roles except admin and can see all modules.",
//                RoleEnum.HEAD, "It does not perform any operations, it can only see all modules.",
//                RoleEnum.USER, "He can only see the Daily Report module. There, he can change and view the report he created during the day he wrote it.");
//
//        Arrays.stream(roleNames).forEach((roleName) -> {
//            Optional<Role> optionalRole = roleRepository.findByName(roleName);
//            optionalRole.ifPresentOrElse(System.out::println, () -> {
//                Role roleToCreate = new Role();
//                roleToCreate.setName(roleName);
//                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
//
//                roleRepository.save(roleToCreate);
//            });
//        });
//    }
//}
