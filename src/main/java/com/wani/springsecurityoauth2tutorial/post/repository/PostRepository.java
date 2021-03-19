package com.wani.springsecurityoauth2tutorial.post.repository;

import com.wani.springsecurityoauth2tutorial.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
