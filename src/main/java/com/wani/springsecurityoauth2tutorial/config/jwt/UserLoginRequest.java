package com.wani.springsecurityoauth2tutorial.config.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginRequest {


    private Type type;
    private String username;
    private String password;
    private String refreshToken;

    @Builder
    public UserLoginRequest(Type type, String username, String password, String refreshToken) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
    }


}
