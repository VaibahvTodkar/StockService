package com.vaibhavtodkar.stockservice.service;

import com.vaibhavtodkar.stockservice.entity.User;
import com.vaibhavtodkar.stockservice.payload.LoginRequest;
import com.vaibhavtodkar.stockservice.payload.RegisterRequest;

public interface AuthService {
	String login(LoginRequest loginDto);

    String register(RegisterRequest registerDto);

	User getUserByUsernameOrEmail(String usernameOrEmail);
}