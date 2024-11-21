package com.example.virtualarenabackend.app.controller;

import com.example.virtualarenabackend.app.dto.*;
import com.example.virtualarenabackend.app.service.ArticleService;
import com.example.virtualarenabackend.app.service.CommentService;
import com.example.virtualarenabackend.app.service.ReplyService;
import com.example.virtualarenabackend.auth.document.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@Tag(name = "Article Management", description = "Endpoints for managing articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private String getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                return ((User) authentication.getPrincipal()).getId();
            }
        } catch (Exception ignored) {}
        return null;
    }

    @Operation(
            summary = "Get featured articles",
            description = "Retrieves top 5 featured articles ordered by publish date"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved featured articles",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticlePreviewDTO.class)))
    )
    @GetMapping("/featured")
    public ResponseEntity<List<ArticlePreviewDTO>> getFeaturedArticles() {
        String currentUserId = getCurrentUserId();
        List<ArticlePreviewDTO> articles = articleService.getFeaturedArticles(currentUserId);
        return ResponseEntity.ok(articles);
    }

    @Operation(
            summary = "Get latest articles",
            description = "Retrieves paginated list of articles ordered by publish date"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved articles"
    )
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestArticles(
            @Parameter(description = "Page number (1-based)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "9")
            @RequestParam(defaultValue = "9") int limit) {

        String currentUserId = getCurrentUserId();

        Page<ArticlePreviewDTO> articlesPage = articleService.getLatestArticles(page, limit, currentUserId);

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articlesPage.getContent());
        response.put("totalPages", articlesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Search articles",
            description = "Search articles based on query string with pagination"
    )
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchArticles(
            @Parameter(description = "Search query string", example = "technology")
            @RequestParam String query,
            @Parameter(description = "Page number", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Results per page", example = "9")
            @RequestParam(defaultValue = "9") int limit) {
        String currentUserId = getCurrentUserId();
        Page<ArticlePreviewDTO> articlesPage = articleService.searchArticles(query, page, limit, currentUserId);

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articlesPage.getContent());
        response.put("totalPages", articlesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Get user's bookmarked articles",
            description = "Retrieves paginated list of articles bookmarked by the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved bookmarked articles"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/bookmarked")
    public ResponseEntity<Map<String, Object>> getBookmarkedArticles(
            @Parameter(description = "Page number (1-based)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "9")
            @RequestParam(defaultValue = "9") int limit,
            @AuthenticationPrincipal User user) {

        Page<ArticlePreviewDTO> articlesPage = articleService.getBookmarkedArticles(page, limit, user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articlesPage.getContent());
        response.put("totalPages", articlesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get article details",
            description = "Retrieve detailed information about a specific article"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved article details"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDetailDTO> getArticleDetail(
            @Parameter(description = "Article ID", example = "123abc")
            @PathVariable String articleId) {
        String currentUserId = getCurrentUserId();

        ArticleDetailDTO article = articleService.getArticleDetail(articleId, currentUserId);
        return ResponseEntity.ok(article);
    }

    @Operation(
            summary = "Create new article",
            description = "Create a new article with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Article created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<Void> createArticle(
            @Parameter(description = "Article creation data", required = true)
            @RequestBody ArticleCreateDTO dto,
            @AuthenticationPrincipal User user) {
        articleService.createArticle(dto, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Toggle heart/like on article",
            description = "Heart or unheart an article. Returns 204 on success"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Heart status updated"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{articleId}/heart")
    public ResponseEntity<Void> heartArticle(
            @Parameter(description = "Article ID")
            @PathVariable String articleId,
            @AuthenticationPrincipal User user) {
        articleService.heartArticle(articleId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Toggle bookmark on article",
            description = "Bookmark or unbookmark an article. Returns 204 on success"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bookmark status updated"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{articleId}/bookmark")
    public ResponseEntity<Void> bookmarkArticle(
            @Parameter(description = "Article ID")
            @PathVariable String articleId,
            @AuthenticationPrincipal User user) {
        articleService.bookmarkArticle(articleId, user.getId());
        return ResponseEntity.noContent().build();
    }



    @Operation(
            summary = "Get user's articles",
            description = "Retrieves paginated list of articles created by the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved user's articles"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getUserArticles(
            @Parameter(description = "Page number (1-based)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "9")
            @RequestParam(defaultValue = "9") int limit,
            @AuthenticationPrincipal User user) {

        Page<ArticlePreviewDTO> articlesPage = articleService.getUserArticles(page, limit, user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articlesPage.getContent());
        response.put("totalPages", articlesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Edit existing article",
            description = "Edit an existing article with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User is not the author"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{articleId}")
    public ResponseEntity<Void> editArticle(
            @Parameter(description = "Article ID", required = true)
            @PathVariable String articleId,
            @Parameter(description = "Article update data", required = true)
            @RequestBody ArticleUpdateDTO dto,
            @AuthenticationPrincipal User user) {
        articleService.updateArticle(articleId, dto, user.getId());
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Delete article",
            description = "Deletes a specific article"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Article deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "403", description = "Not authorized to delete this article")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID of the article to delete") @PathVariable String articleId,
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        articleService.deleteArticle(articleId, user.getId());
        return ResponseEntity.noContent().build();
    }

}

