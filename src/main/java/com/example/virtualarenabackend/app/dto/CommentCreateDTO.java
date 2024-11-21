package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for creating a new comment")
public class CommentCreateDTO {
    @Schema(description = "Content of the comment", example = "This is a great article!")
    private String content;
}
