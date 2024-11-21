package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for reply response")
public class ReplyDTO {
    @Schema(description = "Unique identifier of the reply", example = "507f1f77bcf86cd799439011")
    private String id;
    @Schema(description = "Content of the reply", example = "Thank you for your comment!")
    private String content;
    @Schema(description = "Creation timestamp", example = "2024-01-01T10:30:00")
    private String createdAt;
    @Schema(description = "User who created the reply")
    private UserPreviewDTO user;
    @Schema(description = "Number of hearts/likes", example = "3")
    private int heartCount;
}
