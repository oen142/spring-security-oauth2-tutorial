package com.wani.springsecurityoauth2tutorial.auth.ui;

import com.wani.springsecurityoauth2tutorial.auth.application.AuthService;
import com.wani.springsecurityoauth2tutorial.auth.request.AuthRequest;
import com.wani.springsecurityoauth2tutorial.auth.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<AuthResponse> enroll(
            @RequestBody AuthRequest authRequest
    ) {
        AuthResponse response = authService.enroll(authRequest);
        return ResponseEntity.ok().body(response);
    }
}
