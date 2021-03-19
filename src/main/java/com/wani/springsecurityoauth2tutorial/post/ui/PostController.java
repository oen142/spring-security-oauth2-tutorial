package com.wani.springsecurityoauth2tutorial.post.ui;

import com.wani.springsecurityoauth2tutorial.post.application.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


}
