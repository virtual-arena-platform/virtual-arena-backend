package com.example.virtualarenabackend.auth.controller;

import com.example.virtualarenabackend.auth.document.User;
import com.example.virtualarenabackend.auth.dto.*;
import com.example.virtualarenabackend.auth.service.AuthenticationService;
import com.example.virtualarenabackend.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthController {

    private final AuthenticationService service;
    private final UserService userService;

    @Operation(summary = "Register new user",
            description = "Creates a new user account and returns authentication tokens")
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Authenticate user",
            description = "Authenticates user credentials and returns tokens")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(summary = "Change password",
            description = "Changes the password for authenticated user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            @RequestHeader("Authorization") String token
    ) {
        service.changePassword(request, token);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Refresh authentication token",
            description = "Gets new access token using refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @Operation(summary = "Send email verification code",
            description = "Sends verification code to user's email")
    @PostMapping("/verify-email/send")
    public ResponseEntity<Void> sendVerificationEmail(
            @RequestBody EmailVerificationSendRequest emailVerificationSendRequest
    ) {
        service.sendVerificationEmail(emailVerificationSendRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Verify email address",
            description = "Verifies email address using verification code")
    @PostMapping("/verify-email/verify")
    public ResponseEntity<Void> verifyEmail(
            @RequestBody EmailVerificationRequest request
    ) {
        service.verifyEmail(request);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Get current user details",
            description = "Returns details of authenticated user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<UserDetailDTO> getCurrentUser(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.getCurrentUser(user.getId()));
    }


    @Operation(summary = "Update user profile",
            description = "Updates profile information of authenticated user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/me/update")
    public ResponseEntity<UserDetailDTO> updateUserProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateProfileDTO updateProfileDTO
    ) {
        return ResponseEntity.ok(userService.updateProfile(user.getId(), updateProfileDTO));
    }


}