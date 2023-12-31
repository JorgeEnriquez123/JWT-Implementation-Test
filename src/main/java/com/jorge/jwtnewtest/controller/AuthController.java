package com.jorge.jwtnewtest.controller;

import com.jorge.jwtnewtest.config.security.AuthService;
import com.jorge.jwtnewtest.dto.LoginRequest;
import com.jorge.jwtnewtest.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
