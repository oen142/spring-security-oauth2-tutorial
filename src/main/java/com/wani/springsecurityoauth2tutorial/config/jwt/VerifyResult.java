package com.wani.springsecurityoauth2tutorial.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class VerifyResult {

    private Long id;
    private String username;
    private boolean result;

    @Builder
    public VerifyResult(Long id, String username, boolean result) {
        this.id = id;
        this.username = username;
        this.result = result;
    }
}
