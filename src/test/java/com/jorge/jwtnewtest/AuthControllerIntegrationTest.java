package com.jorge.jwtnewtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorge.jwtnewtest.configuration.security.AuthService;
import com.jorge.jwtnewtest.dto.LoginRequestDto;
import com.jorge.jwtnewtest.dto.LoginResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(AuthController.class)
//@Import(SecurityConfig.class)
//@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void loadingObjectMapper(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void loginEndpoint_Success() throws Exception{
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("Jorge")
                .password("jorge123")
                .build();

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .access_token("jorgeJWT")
                .build();

        when(authService.login(Mockito.any(LoginRequestDto.class))).thenReturn(loginResponseDto);

        System.out.println(authService.login(loginRequestDto).toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", is("jorgeJWT")))
                .andDo(print());
    }
}
