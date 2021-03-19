package com.wani.springsecurityoauth2tutorial.user.response;

import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String username;
    private String profileImage;
    private Map<String, Object> attributes;

    @Builder
    public UserResponse(String username, String profileImage, Map<String, Object> attributes) {
        this.username = username;
        this.profileImage = profileImage;
        this.attributes = attributes;
    }

    public static UserResponse of(PrincipalDetails principal) {
        return UserResponse.builder()
                .username(principal.getUsername())
                .profileImage(principal.getProfileImage())
                .attributes(principal.getAttributes())
                .build();
    }
}
