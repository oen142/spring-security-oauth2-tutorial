package com.wani.springsecurityoauth2tutorial.config.auth;

import com.wani.springsecurityoauth2tutorial.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes; // OAuth 제공자로부터 받은 회원 정보
    private boolean oauth = false;

    private PrincipalDetails(User user) {
        this.user = user;
    }

    private PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
        this.oauth = true;
    }

    public static PrincipalDetails of(User user) {
        return new PrincipalDetails(user);
    }

    public static PrincipalDetails ofOAuth(User user, Map<String, Object> attributes) {
        return new PrincipalDetails(user, attributes);
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> user.getRole().toString());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public String getProfileImage() {
        return user.getPicture();
    }
}
