package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

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
        String todoPic,
        String todoPicName
) {
    public static ReadTodoDetailResponse from(Todo todo) {

        // todoPic은 업로드 시 항상 "_"를 포함한 형식으로 저장됨 (예: "12345_filename.jpg")
        String todoPicName = todo.getTodoPic();
        if (Objects.nonNull(todoPicName) && !todoPicName.isEmpty()) {
            todoPicName = todoPicName.split("_")[1];
        }

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
                .todoPicName(todoPicName)
                .build();
    }
}
