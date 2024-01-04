package com.jorge.jwtnewtest.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    private String username;
    private String password;
}
