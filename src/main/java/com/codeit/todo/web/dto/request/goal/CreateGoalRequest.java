package com.codeit.todo.web.dto.request.goal;

import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateGoalRequest (
        @NotNull
        String title
) {

    public Goal toEntity(String color, User user) {
        return Goal.builder()
                .goalTitle(this.title)
                .color(color)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
