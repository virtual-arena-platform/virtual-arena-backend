package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Article creation data transfer object")
public class ArticleCreateDTO {
    @Schema(description = "Article title", required = true, example = "Introduction to Spring Boot")
    private String title;
    @Schema(description = "Brief description of the article", required = true)
    private String shortDescription;
    @Schema(description = "Detailed description of the article", required = true)
    private String longDescription;
    @Schema(description = "URL of the main article image")
    private String mainImageUrl;
    @Schema(description = "Source URL of the article")
    private String sourceUrl;
}
