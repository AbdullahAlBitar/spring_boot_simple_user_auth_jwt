package com.example.simple_user_auth.services.interfaces;

import com.example.simple_user_auth.models.User;
import com.example.simple_user_auth.models.dto.UserDto;

public interface UserServiceInterface {
    UserDto save(UserDto user);
    User findByEmail(String email);
}
