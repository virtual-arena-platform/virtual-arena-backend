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
@Schema(description = "Request payload for user authentication")
public class AuthenticationRequest {
    @Schema(description = "User's username", example = "john_doe", required = true)
    private String username;
    @Schema(description = "User's password", example = "password123", required = true)
    private String password;
}
