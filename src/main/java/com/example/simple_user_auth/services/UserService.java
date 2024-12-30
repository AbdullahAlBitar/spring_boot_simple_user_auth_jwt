package com.example.simple_user_auth.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.simple_user_auth.models.Role;
import com.example.simple_user_auth.models.User;
import com.example.simple_user_auth.models.dto.UserDto;
import com.example.simple_user_auth.repositories.RoleRepo;
import com.example.simple_user_auth.repositories.UserRepo;
import com.example.simple_user_auth.services.interfaces.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto userDto) {
        Optional<User> user = userRepo.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        Role userRole = roleRepo.findByName("USER");
        if (userRole == null) {
            throw new IllegalStateException("Default role 'USER' is not available. Please initialize roles.");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User newUser = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(roles)
                .build();

        userRepo.save(newUser);

        return UserDto.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .build();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);

        if(user.isPresent()){
            return user.get();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
