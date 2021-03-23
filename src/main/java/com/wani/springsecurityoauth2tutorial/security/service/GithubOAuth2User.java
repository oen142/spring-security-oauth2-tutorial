package com.wani.springsecurityoauth2tutorial.security.service;

import com.wani.springsecurityoauth2tutorial.user.entity.Role;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class GithubOAuth2User implements OAuth2User, UserDetails {

    private final String name;
    private final Set<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    @Builder
    public GithubOAuth2User(String name, Role role, Map<String, Object> attributes) {
        this.name = name;
        this.authorities = Set.of(new SimpleGrantedAuthority(role.getKey()));
        this.attributes = attributes;
    }

    public static GithubOAuth2User of(User user) {
        return GithubOAuth2User.builder()
                .name(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
