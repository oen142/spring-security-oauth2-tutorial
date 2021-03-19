package com.wani.springsecurityoauth2tutorial.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetailsService;
import com.wani.springsecurityoauth2tutorial.config.jwt.JwtUtil;
import com.wani.springsecurityoauth2tutorial.config.ouath.OAuth2DetailsService;
import com.wani.springsecurityoauth2tutorial.config.jwt.JwtCheckFilter;
import com.wani.springsecurityoauth2tutorial.config.jwt.RefreshableJwtLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;
    private final ObjectMapper objectMapper;
    private final PrincipalDetailsService principalDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final CorsConfig corsConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final RefreshableJwtLoginFilter loginFilter = new RefreshableJwtLoginFilter(authenticationManager(), principalDetailsService, jwtUtil, objectMapper);
        final JwtCheckFilter jwtCheckFilter = new JwtCheckFilter(authenticationManager(), principalDetailsService, jwtUtil);
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .maximumSessions(2)
                .and()
                .and()
                .addFilter(loginFilter)
                .addFilter(jwtCheckFilter)
                .httpBasic().disable()
                .formLogin()
                .defaultSuccessUrl("/api")
                .and()
                .authorizeRequests()
                .antMatchers("/user/**", "/post/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") // ROLE_ 는 강제성이 있음. 롤검증시
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/oauth-login")
                .userInfoEndpoint()
                .userService(oAuth2DetailsService);

    }
}
