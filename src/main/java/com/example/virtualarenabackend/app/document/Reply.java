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
@Document(collection = "replies")
@Schema(description = "Reply entity")
public class Reply {
    @Schema(description = "Unique identifier of the reply")
    @Id
    private String id;
    @Schema(description = "Content of the reply")
    private String content;
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    @Schema(description = "User who created the reply")
    private String userId;
    @Schema(description = "Comment this reply belongs to")
    private String commentId;
    @Schema(description = "List of user IDs who hearted this reply")
    @Builder.Default
    private List<String> hearts = new ArrayList<>();
}
