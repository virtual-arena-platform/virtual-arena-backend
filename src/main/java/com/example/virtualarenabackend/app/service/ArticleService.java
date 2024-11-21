package com.example.virtualarenabackend.app.service;

import com.example.virtualarenabackend.app.document.Article;
import com.example.virtualarenabackend.app.dto.*;
import com.example.virtualarenabackend.app.exception.ResourceNotFoundException;
import com.example.virtualarenabackend.app.exception.UnauthorizedException;
import com.example.virtualarenabackend.app.repository.ArticleRepository;
import com.example.virtualarenabackend.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.virtualarenabackend.app.util.TimeUtil.calculateTimeAgo;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    public Page<ArticlePreviewDTO> getLatestArticles(int page, int limit, String currentUserId) {

        Page<Article> articles = articleRepository.findAllByOrderByPublishedAtDesc(
                PageRequest.of(page - 1, limit)
        );
        return articles.map(article -> toPreviewDTO(article, currentUserId));
    }

    public List<ArticlePreviewDTO> getFeaturedArticles(String currentUserId) {
        return articleRepository.findFeaturedArticles()
                .stream().map((article -> toPreviewDTO(article, currentUserId)))
                .collect(Collectors.toList());
    }

    public Page<ArticlePreviewDTO> searchArticles(String query, int page, int limit, String currentUserId) {
        return articleRepository.searchArticles(query, PageRequest.of(page - 1, limit))
                .map(article -> toPreviewDTO(article, currentUserId));
    }

    public Page<ArticlePreviewDTO> getUserArticles(int page, int limit, String userId) {
        Page<Article> articles = articleRepository.findByPublisherIdOrderByPublishedAtDesc(
                userId,
                PageRequest.of(page - 1, limit)
        );
        return articles.map(article -> toPreviewDTO(article, userId));
    }


    public Page<ArticlePreviewDTO> getBookmarkedArticles(int page, int limit, String userId) {
        Page<Article> articles = articleRepository.findByBookmarksContainingOrderByPublishedAtDesc(
                userId,
                PageRequest.of(page - 1, limit)
        );
        return articles.map(article -> toPreviewDTO(article, userId));
    }


    public void updateArticle(String articleId, ArticleUpdateDTO dto, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        // Check if the user is the author of the article
        if (!article.getPublisherId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to edit this article");
        }

        // Update the article fields
        article.setTitle(dto.getTitle());
        article.setShortDescription(dto.getShortDescription());
        article.setLongDescription(dto.getLongDescription());
        article.setMainImageUrl(dto.getMainImageUrl());
        article.setSourceUrl(dto.getSourceUrl());

        articleRepository.save(article);
    }

    public ArticleDetailDTO getArticleDetail(String articleId, String currentUserId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        return toDetailDTO(article, currentUserId);
    }

    public Article createArticle(ArticleCreateDTO dto, String userId) {
        Article article = Article.builder()
                .title(dto.getTitle())
                .shortDescription(dto.getShortDescription())
                .longDescription(dto.getLongDescription())
                .mainImageUrl(dto.getMainImageUrl())
                .sourceUrl(dto.getSourceUrl())
                .publishedAt(LocalDateTime.now())
                .publisherId(userId)
                .build();
        return articleRepository.save(article);
    }

    public void heartArticle(String articleId, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        if (!article.getHearts().contains(userId)) {
            article.getHearts().add(userId);
            articleRepository.save(article);
        } else {
            article.getHearts().remove(userId);
            articleRepository.save(article);
        }
    }




    public void bookmarkArticle(String articleId, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        if (!article.getBookmarks().contains(userId)) {
            article.getBookmarks().add(userId);
            articleRepository.save(article);
        } else {
            article.getBookmarks().remove(userId);
            articleRepository.save(article);
        }
    }

    private ArticlePreviewDTO toPreviewDTO(Article article, String currentUserId) {
        return ArticlePreviewDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .shortDescription(article.getShortDescription())
                .mainImageUrl(article.getMainImageUrl())
                .publishedAt(article.getPublishedAt().toString())
                .timeAgo(calculateTimeAgo(article.getPublishedAt()))
                .publisher(userService.toUserPreviewDTO(userService.getUserById(article.getPublisherId())))
                .heartCount(article.getHearts().size())
                .commentCount(article.getComments().size())
                .bookmarkCount(article.getBookmarks().size())
                .hearted(currentUserId != null && article.getHearts().contains(currentUserId))
                .bookmarked(currentUserId != null && article.getBookmarks().contains(currentUserId))
                .build();
    }

    private ArticleDetailDTO toDetailDTO(Article article, String currentUserId) {
        return ArticleDetailDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .shortDescription(article.getShortDescription())
                .longDescription(article.getLongDescription())
                .mainImageUrl(article.getMainImageUrl())
                .sourceUrl(article.getSourceUrl())
                .publishedAt(article.getPublishedAt().toString())
                .timeAgo(calculateTimeAgo(article.getPublishedAt()))
                .publisher(userService.toUserPreviewDTO(userService.getUserById(article.getPublisherId())))
                .heartCount(article.getHearts().size())
                .hearted(article.getHearts().contains(currentUserId))
                .commentCount(article.getComments().size())
                .bookmarkCount(article.getBookmarks().size())
                .bookmarked(article.getBookmarks().contains(currentUserId))
                .build();
    }

    public void deleteArticle(String articleId, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        if (!article.getPublisherId().equals(userId)) {
            throw new UnauthorizedException("Not authorized to delete this article");
        }

        articleRepository.delete(article);
    }
}

