package com.jorge.jwtnewtest.handler;

import com.jorge.jwtnewtest.dto.ErrorResponse;
import com.jorge.jwtnewtest.exception.security.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginException.class)
    public ErrorResponse authExceptionHandler(LoginException ex) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(403)
                .message(ex.getMessage())
                .build();
    }
}
    /* @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    } */

    // In case we don't handle the AuthenticationException in our service

    /* @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(ex, HttpStatus.FORBIDDEN);
    } */

    // This triggers when you set the SecurityContextHolder and the user has not the right roles
