package com.example.virtualarenabackend.app.document;

import com.example.virtualarenabackend.auth.document.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
@Schema(description = "Comment entity representing user comments on articles")
public class Comment {
    @Schema(description = "Unique identifier of the comment", example = "507f1f77bcf86cd799439011")
    @Id
    private String id;
    @Schema(description = "Content of the comment", example = "This is a great article!")
    private String content;
    @Schema(description = "Timestamp when the comment was created", example = "2024-01-01T10:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "User who created the comment")
    private String userId;
    @Schema(description = "Article this comment belongs to")
    private String articleId;
    @Schema(description = "List of user IDs who hearted this comment")
    @Builder.Default
    private List<String> hearts = new ArrayList<>();
    @Schema(description = "List of user IDs who replied this comment")
    @Builder.Default
    private List<String> replies = new ArrayList<>();
}

