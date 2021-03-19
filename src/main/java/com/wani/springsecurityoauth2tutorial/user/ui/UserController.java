package com.wani.springsecurityoauth2tutorial.user.ui;

import com.wani.springsecurityoauth2tutorial.user.application.UserService;
import com.wani.springsecurityoauth2tutorial.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserResponse> findUsers(){
        UserResponse response = userService.findUsers();
        return ResponseEntity.ok().body(response);
    }

}
