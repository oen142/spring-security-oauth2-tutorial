package com.wani.springsecurityoauth2tutorial.config.ouath;

import java.util.Map;

public class GithubInfo extends OAuth2UserInfo {

    public GithubInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return "GitHub_" + attributes.get("id").toString();
    }

    @Override
    public String getId() {

        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("login");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {

        return (String) attributes.get("login");
    }
}