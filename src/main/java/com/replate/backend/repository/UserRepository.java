package com.replate.backend.repository;

import com.github.javafaker.Bool;
import com.replate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String username);
    List<User> findAllByIdNot(Long id);
    List<User> findAllByIdNotAndUsernameContainingIgnoreCase(Long id, String username);
}
