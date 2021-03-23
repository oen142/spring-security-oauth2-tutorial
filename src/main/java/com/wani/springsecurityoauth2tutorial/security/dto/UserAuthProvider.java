package com.wani.springsecurityoauth2tutorial.security.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserAuthProvider {

    GITHUB("github" , "github");

    private final String displayName;
    private final String oAuth2Name;

    UserAuthProvider(String displayName, String oAuth2Name) {
        this.displayName = displayName;
        this.oAuth2Name = oAuth2Name;
    }

    public static UserAuthProvider of(String oAuth2Name){
        return Arrays.stream(values())
                .filter(it -> it.oAuth2Name.equals(oAuth2Name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원 준비중인 서비스 계정입니다."));
    }
}
