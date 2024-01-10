package com.jorge.jwtnewtest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/secured")
public class SecuredController {
    @GetMapping
    public String securedMessage1(){
        return "Secured Message 1";
    }

    @GetMapping("/userinfo")
    public Map<String, String> userInfo(Authentication auth){
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", auth.getName());
        userInfo.put("roles", auth.getAuthorities().toString());
        return userInfo;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String securedAdmin(){
        return "Message - ADMIN ONLY";
    }
}
