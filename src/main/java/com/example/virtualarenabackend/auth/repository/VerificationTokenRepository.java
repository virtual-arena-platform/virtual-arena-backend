package com.example.virtualarenabackend.auth.repository;

import com.example.virtualarenabackend.auth.document.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {
    Optional<VerificationToken> findByUserIdAndTokenAndUsedFalseAndExpiryDateAfter(
            String userId, String token, LocalDateTime now);
}