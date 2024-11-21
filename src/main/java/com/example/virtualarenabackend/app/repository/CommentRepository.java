package com.example.virtualarenabackend.app.repository;

import com.example.virtualarenabackend.app.document.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findByArticleId(String articleId, Pageable pageable);
    int countByArticleId(String articleId);

}
