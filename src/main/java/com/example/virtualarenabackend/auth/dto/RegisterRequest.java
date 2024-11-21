package com.example.virtualarenabackend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for user registration")
public class RegisterRequest {
    @Schema(description = "Desired username", example = "john_doe", required = true)
    private String username;
    @Schema(description = "Email address", example = "john@example.com", required = true)
    private String email;
    @Schema(description = "Password", example = "password123", required = true)
    private String password;
}
