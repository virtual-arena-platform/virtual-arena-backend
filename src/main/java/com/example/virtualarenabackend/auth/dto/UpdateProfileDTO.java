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
@Schema(description = "Request payload for updating user profile")
public class UpdateProfileDTO {
    @Schema(description = "New username", example = "john_doe_new")
    private String username;
    @Schema(description = "New avatar URL", example = "https://example.com/avatars/john.jpg")
    private String avatarUrl;
}

