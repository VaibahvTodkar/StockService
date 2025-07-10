package com.vaibhavtodkar.stockservice.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vaibhavtodkar.stockservice.entity.Role;
import com.vaibhavtodkar.stockservice.entity.User;
import com.vaibhavtodkar.stockservice.exception.BlogAPIException;
import com.vaibhavtodkar.stockservice.payload.LoginRequest;
import com.vaibhavtodkar.stockservice.payload.RegisterRequest;
import com.vaibhavtodkar.stockservice.repository.RoleRepository;
import com.vaibhavtodkar.stockservice.repository.UserRepository;
import com.vaibhavtodkar.stockservice.security.JwtTokenProvider;
import com.vaibhavtodkar.stockservice.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	    // Injecting dependencies
	 private AuthenticationManager authenticationManager;
	    private UserRepository userRepository;
	    private RoleRepository roleRepository;
	    private PasswordEncoder passwordEncoder;
	    private JwtTokenProvider jwtTokenProvider;


	    public AuthServiceImpl(AuthenticationManager authenticationManager,
	                           UserRepository userRepository,
	                           RoleRepository roleRepository,
	                           PasswordEncoder passwordEncoder,
	                           JwtTokenProvider jwtTokenProvider) {
	        this.authenticationManager = authenticationManager;
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.jwtTokenProvider = jwtTokenProvider;
	    }

	    @Override
	    public String login(LoginRequest loginDto) {

	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String token = jwtTokenProvider.generateToken(authentication);

	        return token;
	    }

	    @Override
	    public String register(RegisterRequest registerDto) {
	    	
	        // add check for username exists in database
	        if(userRepository.existsByUsername(registerDto.getUsername())){
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
	        }

	        // add check for email exists in database
	        if(userRepository.existsByEmail(registerDto.getEmail())){
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
	        }

	        User user = new User();
	        user.setName(registerDto.getName());
	        user.setUsername(registerDto.getUsername());
	        user.setEmail(registerDto.getEmail());
	        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

	        Set<Role> roles = new HashSet<>();
	        Role userRole = roleRepository.findByName("ROLE_USER").get();
	        roles.add(userRole);
	        user.setRoles(roles);

	        userRepository.save(user);

	        System.out.println("User registered successfully: " + user.getUsername());
	        return "{ \"message\" : \"User registered successfully!.\" }";
	    }
	    
	    public User getUserByUsernameOrEmail(String usernameOrEmail) {
	    	
	    		        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
	                .orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND, "User not found with username or email: " + usernameOrEmail));
	        return user;
	    }
}
