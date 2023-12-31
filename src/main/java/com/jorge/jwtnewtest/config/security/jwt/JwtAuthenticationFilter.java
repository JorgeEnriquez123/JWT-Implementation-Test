package com.jorge.jwtnewtest.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.lang.String.format;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwtToken = getTokenFromRequest(request);

        if (jwtToken != null) {
            var username = jwtService.extractUsername(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userAuthenticated = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwtToken, userAuthenticated)) {
                    log.info("Token is Valid");
                    log.info(
                            format(
                                    "Setting SecurityContext with User: %s",
                                    userAuthenticated.getUsername())
                    );
                    setSecurityContext(userAuthenticated);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(UserDetails userAuthenticated) {
        Authentication authtoken = new UsernamePasswordAuthenticationToken(
                userAuthenticated.getUsername(),
                null,
                userAuthenticated.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authtoken);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
