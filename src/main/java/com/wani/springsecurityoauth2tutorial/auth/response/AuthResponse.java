package com.wani.springsecurityoauth2tutorial.auth.response;

import com.wani.springsecurityoauth2tutorial.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AuthResponse {

    private String username;
    private String email;


    public AuthResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static AuthResponse of(User user) {
        return new AuthResponse(user.getUsername(), user.getEmail());
    }
}
