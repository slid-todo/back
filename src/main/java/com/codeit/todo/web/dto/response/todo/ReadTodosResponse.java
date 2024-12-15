package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReadTodosResponse(
        int todoId,
        String goalTitle,
        String todoTitle,
        LocalDate startDate,
        LocalDate endDate,
        String todoStatus,
        String todoLink,
        String todoPic,
        LocalDateTime createdAt,
        List<ReadCompleteResponse> completes
) {
    public static ReadTodosResponse from(Todo todo, List<ReadCompleteResponse> completeResponses) {
        return ReadTodosResponse.builder()
                .todoId(todo.getTodoId())
                .todoLink(todo.getTodoLink())
                .todoStatus(todo.getTodoStatus())
                .goalTitle(todo.getGoal().getGoalTitle())
                .todoTitle(todo.getTodoTitle())
                .startDate(todo.getStartDate())
                .endDate(todo.getEndDate())
                .todoPic(todo.getTodoPic())
                .createdAt(todo.getCreatedAt())
                .completes(completeResponses)
                .build();
    }
}
