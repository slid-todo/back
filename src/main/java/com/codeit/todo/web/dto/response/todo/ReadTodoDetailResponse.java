package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReadTodoDetailResponse(
        int todoId,
        String goalTitle,
        String goalColor,
        String todoTitle,
        LocalDate startDate,
        LocalDate endDate,
        String todoStatus,
        String todoLink,
        String todoPic
) {
    public static ReadTodoDetailResponse from(Todo todo) {
        return ReadTodoDetailResponse.builder()
                .todoId(todo.getTodoId())
                .goalTitle(todo.getGoal().getGoalTitle())
                .goalColor(todo.getGoal().getColor())
                .todoTitle(todo.getTodoTitle())
                .startDate(todo.getStartDate())
                .endDate(todo.getEndDate())
                .todoStatus(todo.getTodoStatus())
                .todoLink(todo.getTodoLink())
                .todoPic(todo.getTodoPic())
                .build();
    }
}
