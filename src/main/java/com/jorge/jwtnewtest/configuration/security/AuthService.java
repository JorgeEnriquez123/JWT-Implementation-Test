package com.jorge.jwtnewtest.configuration.security;

import com.jorge.jwtnewtest.configuration.security.jwt.JwtService;
import com.jorge.jwtnewtest.dto.ErrorResponse;
import com.jorge.jwtnewtest.dto.LoginRequestDto;
import com.jorge.jwtnewtest.dto.LoginResponseDto;
import com.jorge.jwtnewtest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager auth;

    // * If AuthenticationException is not handled, ExceptionTranslationFilter will handle it and trigger the EntryPoint Handler.
    public Object login(LoginRequestDto loginRequestDto){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        try{
            Authentication authResult = auth.authenticate(authToken);
            var authenticatedUser = (User)authResult.getPrincipal();

            return LoginResponseDto.builder()
                    .access_token(jwtService.generateToken(authenticatedUser))
                    .build();
        }
        catch (AuthenticationException ex){
            return ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(401)
                    .message(ex.getMessage())
                    .build();
        }
    }
}
