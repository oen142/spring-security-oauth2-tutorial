package com.wani.springsecurityoauth2tutorial.config.jwt;

public class CannotVerifyJwtTokenException extends IllegalArgumentException {
    public CannotVerifyJwtTokenException(UserLoginRequest userLogin) {
        super("알 수 없는 타입 : " + userLogin.getType());
    }
}
