package com.example.appjwtrealemailauditing.controller.controller;

import com.example.appjwtrealemailauditing.payload.ApiResponse;
import com.example.appjwtrealemailauditing.payload.RegisterDto;
import com.example.appjwtrealemailauditing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?>registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }
}
