package com.wani.springsecurityoauth2tutorial.security.exception;

import org.springframework.security.core.AuthenticationException;

public class GithubOAuth2Exception extends AuthenticationException {

    public GithubOAuth2Exception(String msg, Throwable cause) {
        super(msg, cause);
    }

    public GithubOAuth2Exception(String msg) {
        super(msg);
    }
}
