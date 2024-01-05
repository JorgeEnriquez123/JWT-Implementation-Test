package com.jorge.jwtnewtest.configuration.security;

import com.jorge.jwtnewtest.configuration.security.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@RequiredArgsConstructor
@Component
@Slf4j
public class SecurityManager {
    @Lazy
    private final JwtService jwtService;
    @Lazy
    private final UserDetailsService userDetailsService;

    public Optional<Authentication> authenticate(String jwtToken){
        try {
            var username = jwtService.extractUsername(jwtToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userAuthenticated = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwtToken, userAuthenticated)) {
                    log.info("JWT Token is Valid for User: '{}'", userAuthenticated.getUsername());
                    return of(new UsernamePasswordAuthenticationToken(
                            userAuthenticated.getUsername(),
                            null,
                            userAuthenticated.getAuthorities())
                    );
                }
            }
        } catch (UsernameNotFoundException ex) {
            log.error("User not found: {}", ex.getMessage());
        } catch (Exception ex){
            log.error("JWT token could not be validated. {}", ex.getMessage());
        }
        return empty();
    }
}
