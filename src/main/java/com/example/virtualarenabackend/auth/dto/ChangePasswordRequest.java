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
@Schema(description = "Request payload for changing password")
public class ChangePasswordRequest {
    @Schema(description = "Current password", example = "oldPassword123", required = true)
    private String oldPassword;
    @Schema(description = "New password", example = "newPassword123", required = true)
    private String newPassword;
}
