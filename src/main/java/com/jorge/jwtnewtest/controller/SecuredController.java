package com.jorge.jwtnewtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecuredController {
    @GetMapping
    public String securedMessage1(){
        return "Secured Message 1";
    }
}
