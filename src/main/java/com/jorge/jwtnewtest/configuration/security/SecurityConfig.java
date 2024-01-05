package com.jorge.jwtnewtest.configuration.security;

import com.jorge.jwtnewtest.configuration.security.authhandler.CustomAccessDenied;
import com.jorge.jwtnewtest.configuration.security.authhandler.CustomAuthenticationEntryPoint;
import com.jorge.jwtnewtest.configuration.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                // Public endpoints
                        .requestMatchers("/public/**", "/auth/**")
                        .permitAll()
                // Authenticated endpoints
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(ex -> ex
                // Authentication Error Handling    |   No Logged User
                        .authenticationEntryPoint(customEntryPoint())
                // Authorization Error Handling     |   Logged User has not the right permission
                        .accessDeniedHandler(customAccessDenied()))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public AuthenticationEntryPoint customEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    public AccessDeniedHandler customAccessDenied() {
        return new CustomAccessDenied();
    }
}
