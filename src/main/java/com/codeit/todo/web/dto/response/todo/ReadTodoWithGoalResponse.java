package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReadTodoWithGoalResponse(
        int todoId,
        String todoTitle,
        LocalDate startDate,
        LocalDate endDate,
        String todoStatus,
        String todoLink,
        String todoPic,
        LocalDateTime createdAt,
        int goalId
) {
    public static ReadTodoWithGoalResponse from(Todo todo) {
        return ReadTodoWithGoalResponse.builder()
                .todoId(todo.getTodoId())
                .todoTitle(todo.getTodoTitle())
                .startDate(todo.getStartDate())
                .endDate(todo.getEndDate())
                .todoStatus(todo.getTodoStatus())
                .todoLink(todo.getTodoLink())
                .todoPic(todo.getTodoPic())
                .createdAt(todo.getCreatedAt())
                .goalId(todo.getGoal().getGoalId())
                .build();
    }
}
