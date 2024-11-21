package com.example.virtualarenabackend.app.repository;

import com.example.virtualarenabackend.app.document.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String> {
    Page<Reply> findByCommentIdOrderByCreatedAtDesc(String commentId, Pageable pageable);

    int countByCommentId(String id);
}