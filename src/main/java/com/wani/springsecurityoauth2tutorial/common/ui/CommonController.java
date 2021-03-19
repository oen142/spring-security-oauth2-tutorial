package com.wani.springsecurityoauth2tutorial.common.ui;

import com.wani.springsecurityoauth2tutorial.config.auth.PrincipalDetails;
import com.wani.springsecurityoauth2tutorial.user.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/")
    public ResponseEntity<String> findUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("null = " + null);
            return ResponseEntity.ok().body(authentication.getPrincipal().toString());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principal.getUsername());
        return ResponseEntity.ok().body(authentication.getPrincipal().toString());
    }
}
