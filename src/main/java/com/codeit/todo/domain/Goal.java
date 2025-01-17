package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalId;

    @Column(nullable = false)
    private String goalTitle;

    @Column(length = 50, nullable = false)
    private String color;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    public void update(String title) {
        this.goalTitle = title;
    }

    @Builder
    public Goal(int goalId, String goalTitle, String color, LocalDateTime createdAt, User user) {
        this.goalId = goalId;
        this.goalTitle = goalTitle;
        this.color = color;
        this.createdAt = createdAt;
        this.user = user;
    }
}
