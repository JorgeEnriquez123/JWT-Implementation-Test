package com.jorge.jwtnewtest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {
    LocalDateTime timestamp;
    int status;
    String message;
}
