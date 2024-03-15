package com.crocusoft.teamprojecttracker.config;

import com.crocusoft.teamprojecttracker.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfig corsConfig;
//    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
//                .cors(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                                x.requestMatchers(POST, ("authentication/**")).permitAll()
                                        .requestMatchers(permitSwagger).permitAll()

//                                        .requestMatchers("/api/v1/super-admin/**"
//                                                , "/api/v1/admin/**"
//                                                , "/api/v1/head/**"
//                                                , "/api/v1/employee/**").hasAnyAuthority(Role.SUPER_ADMIN.name())
//
//                                        .requestMatchers("/api/v1/admin/**").hasAnyAuthority(Role.ADMIN.name())
//                                                , "/api/v1/head/**"
//                                                , "/api/v1/employee/**"

//                                        .requestMatchers("/api/v1/head/**").hasRole(Role.HEAD.name())
//
//                                        .requestMatchers("/api/v1/employee/**").hasRole(Role.USER.name())

                                        .anyRequest()
                                        .authenticated()
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout(x ->
//                        x.logoutUrl("/auth/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) ->
//                                        SecurityContextHolder.clearContext()))
                .build();

    }

    public static String[] permitSwagger = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
    };

}