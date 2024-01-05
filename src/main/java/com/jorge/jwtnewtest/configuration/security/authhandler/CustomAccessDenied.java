package com.jorge.jwtnewtest.configuration.security.authhandler;

import com.jorge.jwtnewtest.model.User;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@Slf4j
public class CustomAccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestURI = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        if (requestURI == null) {
            requestURI = request.getRequestURI();
        }
        log.info("Error processing request: HTTP Method = {}, URL = {}, Error = {}",
                request.getMethod(),
                requestURI,
                accessDeniedException.getMessage()
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        jsonBuilder.add("timestamp", LocalDateTime.now().format(formatter));
        jsonBuilder.add("status", 403);
        jsonBuilder.add("message", "Access Denied");

        String json = jsonBuilder.build().toString();
        response.getWriter().write(json);
    }
}
