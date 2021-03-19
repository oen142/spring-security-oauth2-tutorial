package com.wani.springsecurityoauth2tutorial.auth.application;

import com.wani.springsecurityoauth2tutorial.auth.request.AuthRequest;
import com.wani.springsecurityoauth2tutorial.auth.response.AuthResponse;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import com.wani.springsecurityoauth2tutorial.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse enroll(AuthRequest request) {
        User user = User.of(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return AuthResponse.of(user);
    }


}
