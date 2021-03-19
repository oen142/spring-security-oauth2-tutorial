package com.wani.springsecurityoauth2tutorial.config.jwt;

import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetails;
import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetailsService;
import com.wani.springsecurityoauth2tutorial.config.jwt.JwtUtil;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtCheckFilter extends BasicAuthenticationFilter {


    private static final String AUTH_HEADER = "Authentication";
    private static final String BEARER = "Bearer ";

    private final PrincipalDetailsService principalDetailsService;
    private final JwtUtil jwtUtil;


    public JwtCheckFilter(AuthenticationManager authenticationManager, PrincipalDetailsService principalDetailsService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.principalDetailsService = principalDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(AUTH_HEADER);
        if (token == null || !token.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        VerifyResult result = jwtUtil.verify(token.substring(BEARER.length()));
        if (result.isResult()) {
            System.out.println("result.getId() = " + result.getId());
            System.out.println("result.getId() = " + result.getUsername());
            PrincipalDetails user = principalDetailsService.findUserAuth(result.getUsername());
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
            );

        }

        chain.doFilter(request, response);
    }
}
