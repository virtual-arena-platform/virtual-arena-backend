package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for user preview information")
public class UserPreviewDTO {
    @Schema(description = "User's unique identifier", example = "507f1f77bcf86cd799439011")
    private String id;
    @Schema(description = "User's username", example = "john_doe")
    private String username;
    @Schema(description = "URL of user's avatar", example = "https://example.com/avatars/john_doe.jpg")
    private String avatarUrl;
    @Schema(description = "Whether the user is verified", example = "true")
    private boolean verified;
}
