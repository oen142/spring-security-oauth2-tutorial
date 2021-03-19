package com.wani.springsecurityoauth2tutorial.config.jwt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginResponse {
    private final String accessToken;
    private final String refreshToken;


    public static LoginResponse of(String accessToken, String refreshToken) {
        return new LoginResponse(accessToken, refreshToken);
    }
}
