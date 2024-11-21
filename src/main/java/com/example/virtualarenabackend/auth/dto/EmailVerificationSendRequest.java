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
@Schema(description = "Request to send email verification code")
public class EmailVerificationSendRequest {
    @Schema(description = "Email address to send verification code", example = "user@example.com", required = true)
    private String email;
}
