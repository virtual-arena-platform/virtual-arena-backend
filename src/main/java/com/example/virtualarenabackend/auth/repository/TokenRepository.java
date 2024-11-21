package com.example.virtualarenabackend.auth.repository;

import com.example.virtualarenabackend.auth.document.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {
    @Query("{'userId': ?0, 'expired': false, 'revoked': false}")
    List<Token> findAllValidTokensByUser(String userId);

    Optional<Token> findByToken(String token);
}