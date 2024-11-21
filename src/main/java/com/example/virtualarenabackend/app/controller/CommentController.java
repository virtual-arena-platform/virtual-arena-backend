package com.example.virtualarenabackend.app.controller;

import com.example.virtualarenabackend.app.dto.CommentCreateDTO;
import com.example.virtualarenabackend.app.dto.CommentDTO;
import com.example.virtualarenabackend.app.service.CommentService;
import com.example.virtualarenabackend.auth.document.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Comment management APIs")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Get article comments",
            description = "Retrieves paginated comments for a specific article"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved comments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @GetMapping("/article/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> getArticleComments(
            @Parameter(description = "ID of the article") @PathVariable String articleId,
            @Parameter(description = "Page number (starts from 1)") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int limit) {
        Page<CommentDTO> comments = commentService.getArticleComments(articleId, page, limit);
        return ResponseEntity.ok(comments.getContent());
    }

    @Operation(
            summary = "Create comment",
            description = "Creates a new comment for a specific article"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/article/{articleId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @Parameter(description = "ID of the article") @PathVariable String articleId,
            @Parameter(description = "Comment details") @RequestBody CommentCreateDTO dto,
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.createComment(articleId, dto, user.getId()));
    }

    @Operation(
            summary = "Delete comment",
            description = "Deletes a specific comment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "403", description = "Not authorized to delete this comment")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID of the comment to delete") @PathVariable String commentId,
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
