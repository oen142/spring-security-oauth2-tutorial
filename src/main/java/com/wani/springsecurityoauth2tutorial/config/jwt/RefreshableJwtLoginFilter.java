package com.wani.springsecurityoauth2tutorial.config.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetails;
import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetailsService;
import com.wani.springsecurityoauth2tutorial.config.jwt.JwtUtil;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefreshableJwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String UTF_8 = "UTF-8";
    private static final String APPLICATION_JSON = "application/json";
    private final AuthenticationManager authenticationManager;
    private final PrincipalDetailsService principalDetailsService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public RefreshableJwtLoginFilter(AuthenticationManager authenticationManager, PrincipalDetailsService principalDetailsService, JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.principalDetailsService = principalDetailsService;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginRequest userLogin = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
        if (userLogin.getType().equals(Type.login)) {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword(), null));
        } else if (userLogin.getType().equals(Type.refresh)) {
            validRefreshToken(userLogin);

            VerifyResult result = jwtUtil.verify(userLogin.getRefreshToken());
            if (result.isResult()) {
                PrincipalDetails user = principalDetailsService.findUserAuth(result.getUsername());
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            }

            throw new TokenExpiredException("리프레시 토큰 만료");
        } else {
            throw new CannotVerifyJwtTokenException(userLogin);
        }
    }


    private void validRefreshToken(UserLoginRequest userLogin) {
        if (StringUtils.isEmpty(userLogin.getRefreshToken())) {
            throw new JwtRefreshTokenException(userLogin.getRefreshToken());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();

        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);

        String accessToken = jwtUtil.generateAccessToken(principal.getId(), principal.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(principal.getId(), principal.getUsername());

        response.getWriter()
                .write(
                        new Gson().toJson(
                                LoginResponse.of(accessToken, refreshToken)));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
