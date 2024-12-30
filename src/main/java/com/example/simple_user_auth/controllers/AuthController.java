package com.example.simple_user_auth.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.simple_user_auth.models.Role;
import com.example.simple_user_auth.models.User;
import com.example.simple_user_auth.models.dto.UserDto;
import com.example.simple_user_auth.services.JwtUtil;
import com.example.simple_user_auth.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto loginReq) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

            User userDetails = userService.findByEmail(loginReq.getEmail());
            String token = JwtUtil.generateToken(userDetails.getEmail(), userDetails.getRoles().stream().map(Role::getName).toList());

            return ResponseEntity.ok().body(Map.of("token", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto registerReq) {
        try {
            return ResponseEntity.ok().body(Map.of("User", userService.save(registerReq)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(500).body(Map.of("error", "An unknown error occurred."));
        }
    }

}
