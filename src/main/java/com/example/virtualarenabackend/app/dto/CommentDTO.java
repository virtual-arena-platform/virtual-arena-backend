package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for comment response")
public class CommentDTO {
    @Schema(description = "Unique identifier of the comment", example = "507f1f77bcf86cd799439011")
    private String id;
    @Schema(description = "Content of the comment", example = "This is a great article!")
    private String content;
    @Schema(description = "Creation timestamp", example = "2024-01-01T10:30:00")
    private String createdAt;
    @Schema(description = "User who created the comment")
    private UserPreviewDTO user;
    @Schema(description = "Number of hearts/likes", example = "5")
    private int heartCount;
    @Schema(description = "Number of replies", example = "3")
    private int replyCount;
}
