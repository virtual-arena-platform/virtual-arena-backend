package com.example.virtualarenabackend.auth.service;

import com.example.virtualarenabackend.app.dto.UserPreviewDTO;
import com.example.virtualarenabackend.app.exception.ResourceNotFoundException;
import com.example.virtualarenabackend.auth.document.User;
import com.example.virtualarenabackend.auth.dto.UpdateProfileDTO;
import com.example.virtualarenabackend.auth.dto.UserDetailDTO;
import com.example.virtualarenabackend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserDetailDTO getCurrentUser(String userId) {
        User user = getUserById(userId);
        return toUserDetailDTO(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDetailDTO updateProfile(String userId, UpdateProfileDTO dto) {
        User user = getUserById(userId);

        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new IllegalArgumentException("Username already taken");
            }
            user.setUsername(dto.getUsername());
        }

        if (dto.getAvatarUrl() != null) {
            user.setAvatarUrl(dto.getAvatarUrl());
        }

        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        return toUserDetailDTO(user);
    }

    public Page<UserPreviewDTO> searchUsers(String query, int page, int size) {
        return userRepository.findByUsernameContaining(
                        query, PageRequest.of(page - 1, size))
                .map(this::toUserPreviewDTO);
    }

    public UserPreviewDTO toUserPreviewDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserPreviewDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .verified(user.isVerified())
                .build();
    }



    private UserDetailDTO toUserDetailDTO(User user) {
        return UserDetailDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .verified(user.isVerified())
                .createdAt(user.getCreatedAt().toString())
                .build();
    }
}