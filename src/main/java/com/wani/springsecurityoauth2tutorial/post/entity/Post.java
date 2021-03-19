package com.wani.springsecurityoauth2tutorial.post.entity;

import com.wani.springsecurityoauth2tutorial.common.entity.BaseTimeEntity;
import com.wani.springsecurityoauth2tutorial.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false , length = 100)

    @Lob
    private String content;

    @ColumnDefault("0")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "userId")
    private User user;


}
