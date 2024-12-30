package com.example.simple_user_auth.config;

import com.example.simple_user_auth.models.Role;
import com.example.simple_user_auth.repositories.RoleRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;

    public RoleInitializer(RoleRepo roleRepository) {
        this.roleRepo = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> defaultRoles = Arrays.asList("ADMIN", "USER", "SUPER_ADMIN");

        for (String roleName : defaultRoles) {
            if (roleRepo.findByName(roleName) == null) {
                roleRepo.save(new Role(null, roleName));
                System.out.println("Inserted default role: " + roleName);
            }
        }
    }
}
