package com.wani.springsecurityoauth2tutorial.user.entity;

import com.wani.springsecurityoauth2tutorial.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column(nullable = false)
    @Lob
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Builder
    private User(String username, String email, String picture, String password, Role role) {
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.role = role;
    }

    public static User of(String username, String email, String encodedPassword) {
        return User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }

    public User update(String username, String picture) {
        this.username = username;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
