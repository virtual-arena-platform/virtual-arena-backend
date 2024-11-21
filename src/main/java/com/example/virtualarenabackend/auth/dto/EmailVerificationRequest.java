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
@Schema(description = "Request payload for email verification")
public class EmailVerificationRequest {
    @Schema(description = "User's email address", example = "user@example.com", required = true)
    private String email;
    @Schema(description = "Verification code", example = "123456", required = true)
    private String code;
}
