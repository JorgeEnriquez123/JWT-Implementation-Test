package com.jorge.jwtnewtest;


import com.jorge.jwtnewtest.configuration.security.AuthService;
import com.jorge.jwtnewtest.configuration.security.jwt.JwtService;
import com.jorge.jwtnewtest.dto.LoginRequestDto;
import com.jorge.jwtnewtest.dto.LoginResponseDto;
import com.jorge.jwtnewtest.exception.security.LoginException;
import com.jorge.jwtnewtest.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager auth;

    @InjectMocks
    AuthService authService;

    private LoginRequestDto loginRequestDto;
    private UsernamePasswordAuthenticationToken authToken;

    @BeforeEach
    public void init(){
        loginRequestDto = LoginRequestDto.builder()
                .username("testUser")
                .password("testPassword")
                .build();
        authToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
    }

    @Test
    public void authService_LoginSuccessful(){
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .build();

        when(auth.authenticate(authToken)).thenReturn(new TestingAuthenticationToken(user, null));
        when(jwtService.generateToken(user)).thenReturn("testJWT");

        Object result = authService.login(loginRequestDto);

        assertTrue(result instanceof LoginResponseDto);
        assertEquals("testJWT", ((LoginResponseDto) result).getAccess_token());
    }

    @Test
    public void authService_LoginFailed(){
        when(auth.authenticate(authToken)).thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(LoginException.class, () -> {
            authService.login(loginRequestDto);
        });
    }
}
