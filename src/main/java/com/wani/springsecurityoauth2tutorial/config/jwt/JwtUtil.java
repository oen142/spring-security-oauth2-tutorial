package com.wani.springsecurityoauth2tutorial.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtUtil {

    private static final long tokenValidMS = 1000L * 60 * 60;
    private static final String AUTH_HEADER = "Authentication";
    private static final String REFRESH_HEADER = "refresh-token";
    private static final String BEARER = "Bearer ";
    private static final String SECRET = "Secret";
    private final static long TOKEN_LIFE_TIME = 60000;
    private final static long REFRESH_TIME = 24 * 60 * 60;

    private Algorithm AL = Algorithm.HMAC512(SECRET);

    private enum TokenType {
        access,
        refresh
    }

    public String generateAccessToken(Long id, String username) {
        return BEARER + generate(id, username, TokenType.access);
    }

    public String generateRefreshToken(Long id, String username) {
        return BEARER + generate(id, username, TokenType.refresh);
    }

    private String generate(Long id, String username, TokenType type) {
        return JWT.create()
                .withSubject(username)
                .withClaim("userId", id)
                .withClaim("exp", Instant.now().getEpochSecond() + getLifeTime(type))
                .sign(AL);
    }

    private long getLifeTime(TokenType type) {
        if (type == TokenType.access) {
            return REFRESH_TIME;
        }

        return TOKEN_LIFE_TIME;
    }

    public VerifyResult verify(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return VerifyResult.builder()
                    .username(decodedJWT.getSubject())
                    .result(true)
                    .build();

        } catch (JWTVerificationException ex) {
            DecodedJWT decodedJWT = JWT.decode(token);
            return VerifyResult.builder()
                    .username(decodedJWT.getSubject())
                    .result(false)
                    .build();
        }

    }
}
