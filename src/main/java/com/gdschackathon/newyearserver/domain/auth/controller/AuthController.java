package com.gdschackathon.newyearserver.domain.auth.controller;

import com.gdschackathon.newyearserver.domain.auth.dto.LoginRequest;
import com.gdschackathon.newyearserver.domain.auth.dto.LoginResponse;
import com.gdschackathon.newyearserver.domain.auth.dto.RegisterRequest;
import com.gdschackathon.newyearserver.domain.auth.dto.RegisterResponse;
import com.gdschackathon.newyearserver.domain.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        authService.register(request.personalToken(), request.email());

        return new RegisterResponse("회원가입 성공");
    }

    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return new LoginResponse(authService.login(request.personalToken()));
    }
}
