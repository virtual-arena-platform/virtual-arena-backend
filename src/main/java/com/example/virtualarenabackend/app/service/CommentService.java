package com.example.virtualarenabackend.app.service;

import com.example.virtualarenabackend.app.document.Article;
import com.example.virtualarenabackend.app.document.Comment;
import com.example.virtualarenabackend.app.dto.CommentCreateDTO;
import com.example.virtualarenabackend.app.dto.CommentDTO;
import com.example.virtualarenabackend.app.exception.ResourceNotFoundException;
import com.example.virtualarenabackend.app.exception.UnauthorizedException;
import com.example.virtualarenabackend.app.repository.ArticleRepository;
import com.example.virtualarenabackend.app.repository.CommentRepository;
import com.example.virtualarenabackend.app.repository.ReplyRepository;
import com.example.virtualarenabackend.auth.document.User;
import com.example.virtualarenabackend.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    public Page<CommentDTO> getArticleComments(String articleId, int page, int limit) {
        return commentRepository.findByArticleId(articleId, PageRequest.of(page - 1, limit))
                .map(this::toCommentDTO);
    }

    public CommentDTO createComment(String articleId, CommentCreateDTO dto, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .articleId(articleId)
                .build();

        Comment savedComment = commentRepository.save(comment);

        article.getComments().add(savedComment.getId());
        articleRepository.save(article);

        return toCommentDTO(savedComment);
    }

    public void deleteComment(String commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new UnauthorizedException("Not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    private CommentDTO toCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().toString())
                .user(userService.toUserPreviewDTO(userService.getUserById(comment.getUserId())))
                .heartCount(comment.getHearts().size())
                .replyCount(comment.getReplies().size())
                .build();
    }
}
