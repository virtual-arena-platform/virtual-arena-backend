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
@Document(collection = "articles")
@Schema(description = "Article entity representing published content")
public class Article {
    @Schema(description = "Unique identifier of the article", example = "507f1f77bcf86cd799439011")
    @Id
    private String id;
    @Schema(description = "Title of the article", example = "Introduction to Virtual Reality")
    private String title;
    @Schema(description = "Brief description of the article", example = "A quick overview of VR technology")
    private String shortDescription;
    @Schema(description = "Detailed content of the article", example = "Virtual Reality (VR) is a technology that...")
    private String longDescription;
    @Schema(description = "URL of the main article image", example = "https://example.com/images/vr-intro.jpg")
    private String mainImageUrl;
    @Schema(description = "Original source URL of the article", example = "https://example.com/original-article")
    private String sourceUrl;
    @Schema(description = "Publication timestamp", example = "2024-01-01T10:30:00")
    private LocalDateTime publishedAt;
    @Schema(description = "User who published the article")
    private String publisherId;
    @Schema(description = "List of user IDs who hearted this article")
    @Builder.Default
    private List<String> hearts = new ArrayList<>();
    @Schema(description = "List of user IDas who bookmarked this article")
    @Builder.Default
    private List<String> bookmarks = new ArrayList<>();
    @Schema(description = "List of comment IDs on the article")
    @Builder.Default
    private List<String> comments = new ArrayList<>();

}