package com.vaibhavtodkar.stockservice.controller;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaibhavtodkar.stockservice.entity.User;
import com.vaibhavtodkar.stockservice.payload.JWTAuthResponse;
import com.vaibhavtodkar.stockservice.payload.LoginRequest;
import com.vaibhavtodkar.stockservice.payload.RegisterRequest;
import com.vaibhavtodkar.stockservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping("/hello")
	public String hello() {
		return "Public Hello from Spring Boot!";
	}

    // Build Login REST API
    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginRequest loginDto){
        String token = authService.login(loginDto);
        
        User user = authService.getUserByUsernameOrEmail(loginDto.getUsernameOrEmail());
        
        Set<String> roles =  user
                .getRoles()
                .stream()
                .map((role) -> role.getName()).collect(Collectors.toSet());
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRoles(roles);
        jwtAuthResponse.setUsername(user.getUsername());

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register"})
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}