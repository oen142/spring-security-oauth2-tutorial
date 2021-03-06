package com.wani.springsecurityoauth2tutorial.config.auth;

import com.wani.springsecurityoauth2tutorial.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return PrincipalDetails.of(userRepository.findByUsername(username));
    }

    public PrincipalDetails findUserAuth(String username) {
        return PrincipalDetails.of(userRepository.findByUsername(username)
        );
    }
}
