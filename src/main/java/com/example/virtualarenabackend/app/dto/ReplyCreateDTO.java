package com.example.virtualarenabackend.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for creating a new reply")
public class ReplyCreateDTO {
    @Schema(description = "Content of the reply", example = "Thank you for your comment!")
    private String content;
}
