package com.example.virtualarenabackend.app.controller;

import com.example.virtualarenabackend.app.dto.ReplyCreateDTO;
import com.example.virtualarenabackend.app.dto.ReplyDTO;
import com.example.virtualarenabackend.app.service.ReplyService;
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
@Tag(name = "Replies", description = "Reply management APIs")
public class ReplyController {
    private final ReplyService replyService;

    @Operation(
            summary = "Get replies for a comment",
            description = "Retrieves paginated replies for a specific comment"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved replies",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReplyDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @GetMapping("/comment/{commentId}/replies")
    public ResponseEntity<List<ReplyDTO>> getReplies(
            @Parameter(description = "ID of the comment") @PathVariable String commentId,
            @Parameter(description = "Page number (starts from 1)") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int limit) {
        Page<ReplyDTO> replies = replyService.getReplies(commentId, page, limit);
        return ResponseEntity.ok(replies.getContent());
    }

    @Operation(
            summary = "Create reply",
            description = "Creates a new reply for a specific comment"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reply created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReplyDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/comment/{commentId}/replies")
    public ResponseEntity<ReplyDTO> createReply(
            @Parameter(description = "ID of the comment")
            @PathVariable String commentId,
            @Parameter(description = "Reply details")
            @RequestBody ReplyCreateDTO dto,
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(replyService.createReply(commentId, dto, user.getId()));
    }


    @Operation(
            summary = "Delete reply",
            description = "Deletes a specific reply"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reply deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reply not found"),
            @ApiResponse(responseCode = "403", description = "Not authorized to delete this reply")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{replyId}/reply")
    public ResponseEntity<Void> deleteReply(
            @Parameter(description = "ID of the reply to delete")
            @PathVariable String replyId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user) {
        replyService.deleteReply(replyId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
