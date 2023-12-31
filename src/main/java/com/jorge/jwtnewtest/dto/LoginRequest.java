package com.jorge.jwtnewtest.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
