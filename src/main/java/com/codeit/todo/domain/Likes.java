package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complete_id", nullable = false)
    private Complete complete;

    @Builder
    public Likes(int likesId, User user, Complete complete) {
        this.likesId = likesId;
        this.user = user;
        this.complete = complete;
    }

    public static Likes toEntity(User user, Complete complete) {
        return Likes.builder()
                .user(user)
                .complete(complete)
                .build();
    }
}
