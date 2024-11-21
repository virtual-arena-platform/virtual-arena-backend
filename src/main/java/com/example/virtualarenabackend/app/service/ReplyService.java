package com.example.virtualarenabackend.app.service;

import com.example.virtualarenabackend.app.document.Reply;
import com.example.virtualarenabackend.app.document.Comment;
import com.example.virtualarenabackend.app.dto.ReplyDTO;
import com.example.virtualarenabackend.app.dto.ReplyCreateDTO;
import com.example.virtualarenabackend.app.exception.ResourceNotFoundException;
import com.example.virtualarenabackend.app.exception.UnauthorizedException;
import com.example.virtualarenabackend.app.repository.ReplyRepository;
import com.example.virtualarenabackend.app.repository.CommentRepository;
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
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public Page<ReplyDTO> getReplies(String commentId, int page, int limit) {
        Page<Reply> replies = replyRepository.findByCommentIdOrderByCreatedAtDesc(
                commentId,
                PageRequest.of(page - 1, limit)
        );
        return replies.map(this::toReplyDTO);
    }

    public ReplyDTO createReply(String commentId, ReplyCreateDTO dto, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        Reply reply = Reply.builder()
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .commentId(commentId)
                .build();

        Reply savedReply = replyRepository.save(reply);

        comment.getReplies().add(savedReply.getId());
        commentRepository.save(comment);

        return toReplyDTO(savedReply);
    }

    public void heartReply(String replyId, String userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));

        if (!reply.getHearts().contains(userId)) {
            reply.getHearts().add(userId);
            replyRepository.save(reply);
        }
    }

    private ReplyDTO toReplyDTO(Reply reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt().toString())
                .user(userService.toUserPreviewDTO(userService.getUserById(reply.getUserId())))
                .heartCount(reply.getHearts().size())
                .build();
    }

    public void deleteReply(String replyId, String userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));

        if (!reply.getUserId().equals(userId)) {
            throw new UnauthorizedException("Not authorized to delete this reply");
        }

        replyRepository.delete(reply);
    }
}