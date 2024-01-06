package com.jorge.jwtnewtest.configuration.security.authhandler;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import static java.lang.String.format;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestURI = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        if (requestURI == null) {
            requestURI = request.getRequestURI();
        }
        log.info("Error processing request: HTTP Method = {}, URI = {}, Error = {}",
                request.getMethod(),
                requestURI,
                authException.getMessage()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("timestamp", LocalDateTime.now().format(formatter));
        jsonBuilder.add("status", 401);
        jsonBuilder.add("message", authException.getMessage());

        String json = jsonBuilder.build().toString();
        response.getWriter().write(json);
    }
}
