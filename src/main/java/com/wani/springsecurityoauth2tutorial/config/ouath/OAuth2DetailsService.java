package com.wani.springsecurityoauth2tutorial.config.ouath;

import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetails;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import com.wani.springsecurityoauth2tutorial.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getClientName().equals("Google")) {
        } else if (userRequest.getClientRegistration().getClientName().equals("Facebook")) {
        } else if (userRequest.getClientRegistration().getClientName().equals("Naver")) {
            oAuth2UserInfo = new NaverInfo((Map) (oAuth2User.getAttributes().get("response")));
        } else if (userRequest.getClientRegistration().getClientName().equals("Kakao")) {
            oAuth2UserInfo = new KakaoInfo((Map) (oAuth2User.getAttributes()));
        } else if (userRequest.getClientRegistration().getClientName().equals("GitHub")) {
            oAuth2UserInfo = new GithubInfo((Map) (oAuth2User.getAttributes()));
        }

        System.out.println("oAuth2UserInfo = " + userRequest.getClientRegistration().getClientName());
        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.forEach(
                (it, st) -> {
                    System.out.println("it = " + it);
                    System.out.println("st = " + st);
                }
        );
        User user = userRepository.findByUsername(oAuth2UserInfo.getUsername());

        UUID uuid = UUID.randomUUID();
        String encPassword = new BCryptPasswordEncoder().encode(uuid.toString());

        if (user == null) {

            validUsername(oAuth2UserInfo);
            validEmail(oAuth2UserInfo);
            User newUser = User.of(
                    oAuth2UserInfo.getUsername(), oAuth2UserInfo.getEmail(), encPassword
            );
            userRepository.save(newUser);
            PrincipalDetails principalUser = PrincipalDetails.ofOAuth(newUser, oAuth2User.getAttributes());
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities())
            );
            return principalUser;

        } else {
            PrincipalDetails principalUser = PrincipalDetails.ofOAuth(user, oAuth2User.getAttributes());
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities())
            );
            return principalUser;
        }
    }

    private void validEmail(OAuth2UserInfo oAuth2UserInfo) {
        if(oAuth2UserInfo.getEmail() == null){
            throw new RuntimeException("이메일이 널입니다.");
        }
    }

    private void validUsername(OAuth2UserInfo oAuth2UserInfo) {
        if(oAuth2UserInfo.getUsername() == null){
            throw new RuntimeException("유저네임이 널입니다.");
        }
    }
}
