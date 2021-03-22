package com.wani.springsecurityoauth2tutorial.user.application;

import com.wani.springsecurityoauth2tutorial.security.dto.OAuth2AttributeDto;
import com.wani.springsecurityoauth2tutorial.security.dto.UserAuthProvider;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import com.wani.springsecurityoauth2tutorial.user.repository.UserRepository;
import com.wani.springsecurityoauth2tutorial.user.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse findUsers() {
        return null;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public User upsert(OAuth2AttributeDto oAuth2Attribute) {
        UserAuthProvider provider = UserAuthProvider.of(oAuth2Attribute.getProvider());
        User user = userRepository.findByAuthDetailProviderAndAuthDetailKey(provider , oAuth2Attribute.getProviderKey());

        return null;
    }
}
