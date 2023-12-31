package com.jorge.jwtnewtest.config.security;

import com.jorge.jwtnewtest.config.security.jwt.JwtService;
import com.jorge.jwtnewtest.dto.LoginRequest;
import com.jorge.jwtnewtest.dto.LoginResponse;
import com.jorge.jwtnewtest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager auth;
    public LoginResponse login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authResult = auth.authenticate(authToken);
        var authenticatedUser = (User)authResult.getPrincipal();

        return LoginResponse.builder()
                .access_token(jwtService.generateToken(authenticatedUser))
                .build();
    }
}
