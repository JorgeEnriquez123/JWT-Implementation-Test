package com.jorge.jwtnewtest.controller;

import com.jorge.jwtnewtest.configuration.security.AuthService;
import com.jorge.jwtnewtest.dto.LoginRequestDto;
import com.jorge.jwtnewtest.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return authService.login(loginRequestDto);
    }
}
