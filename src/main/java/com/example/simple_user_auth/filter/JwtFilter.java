package com.example.simple_user_auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.simple_user_auth.services.JwtUtil;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = JwtUtil.validateToken(token);
            if (username != null) {
                // Authenticate the user (optional)
                request.setAttribute("username", username);
            }
        }
        filterChain.doFilter(request, response);
    }
}

