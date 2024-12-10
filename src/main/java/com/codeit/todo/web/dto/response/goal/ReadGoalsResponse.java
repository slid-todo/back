package com.codeit.todo.web.dto.response.goal;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadGoalsResponse(
        int goalId,
        String goalTitle,
        String color,
        LocalDateTime createdAt




) {

}
