package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Article detail data transfer object")
public class ArticleDetailDTO {
    @Schema(description = "Unique identifier of the article", example = "123abc")
    private String id;
    @Schema(description = "Article title", example = "Introduction to Spring Boot")
    private String title;
    @Schema(description = "Brief description of the article")
    private String shortDescription;
    @Schema(description = "Detailed description of the article")
    private String longDescription;
    @Schema(description = "URL of the main article image")
    private String mainImageUrl;
    @Schema(description = "Source URL of the article")
    private String sourceUrl;
    @Schema(description = "Publication timestamp", example = "2024-01-01T12:00:00")
    private String publishedAt;
    @Schema(description = "Human-readable time since publication", example = "2 hours ago")
    private String timeAgo;
    @Schema(description = "Publisher information")
    private UserPreviewDTO publisher;
    @Schema(description = "Number of hearts/likes")
    private int heartCount;
    @Schema(description = "Whether the current user has hearted this article")
    private boolean hearted;
    @Schema(description = "Number of comments")
    private int commentCount;
    @Schema(description = "Number of bookmarks")
    private int bookmarkCount;
    @Schema(description = "Whether the current user has bookmarked this article")
    private boolean bookmarked;
}
