package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complete_id", nullable = false)
    private Complete complete;

    @Builder
    public Comment(int commentId, String content, LocalDateTime createdAt, User user, Complete complete) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.complete = complete;
    }

    public void update(String content){
        this.content = content;
    }
}
