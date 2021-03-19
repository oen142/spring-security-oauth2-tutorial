package com.wani.springsecurityoauth2tutorial.user.repository;

import com.wani.springsecurityoauth2tutorial.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
