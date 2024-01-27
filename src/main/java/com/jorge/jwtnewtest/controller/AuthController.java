    package com.jorge.jwtnewtest.controller;

    import com.jorge.jwtnewtest.configuration.security.AuthService;
    import com.jorge.jwtnewtest.dto.ErrorResponse;
    import com.jorge.jwtnewtest.dto.LoginRequestDto;
    import com.jorge.jwtnewtest.dto.LoginResponseDto;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RequiredArgsConstructor
    @RestController
    @RequestMapping("/auth")
    public class AuthController {
        private final AuthService authService;
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
            Object response = authService.login(loginRequestDto);

            if(response instanceof ErrorResponse){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            return ResponseEntity.ok().body(response);
        }
    }
