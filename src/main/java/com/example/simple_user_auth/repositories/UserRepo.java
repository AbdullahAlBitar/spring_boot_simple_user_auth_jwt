package com.example.simple_user_auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simple_user_auth.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
