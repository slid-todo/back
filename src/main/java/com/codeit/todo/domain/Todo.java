package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todoId;

    @Column(nullable = false)
    private String todoTitle;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(length = 50, nullable = false)
    private Boolean todoStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String todoLink;
    private String todoPic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    @Builder
    public Todo(int todoId, String todoTitle, LocalDate startDate, LocalDate endDate, Boolean todoStatus, LocalDateTime createdAt, String todoLink, String todoPic, Goal goal) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.todoStatus = todoStatus;
        this.createdAt = createdAt;
        this.todoLink = todoLink;
        this.todoPic = todoPic;
        this.goal = goal;
    }
}
