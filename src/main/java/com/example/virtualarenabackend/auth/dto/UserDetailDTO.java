package com.example.virtualarenabackend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed user information")
public class UserDetailDTO {
    @Schema(description = "User's unique identifier", example = "507f1f77bcf86cd799439011")
    private String id;
    @Schema(description = "User's email address", example = "john@example.com")
    private String email;
    @Schema(description = "Username", example = "john_doe")
    private String username;
    @Schema(description = "Avatar URL", example = "https://example.com/avatars/john.jpg")
    private String avatarUrl;
    @Schema(description = "Email verification status", example = "true")
    private boolean verified;
    @Schema(description = "Account creation timestamp", example = "2024-01-01T10:30:00")
    private String createdAt;
}
