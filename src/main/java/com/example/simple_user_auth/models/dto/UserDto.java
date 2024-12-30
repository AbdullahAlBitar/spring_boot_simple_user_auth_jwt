package com.example.simple_user_auth.models.dto;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDto {
    private Long id;

    @Email
    private String email;
    
    private String password;
}
