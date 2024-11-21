package com.example.virtualarenabackend.auth.repository;

import com.example.virtualarenabackend.auth.document.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<User> findByUsernameContaining(
            String username, Pageable pageable);
    Optional<User> findByEmail(String email);

}

