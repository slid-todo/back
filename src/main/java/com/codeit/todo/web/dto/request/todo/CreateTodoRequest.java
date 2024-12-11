package com.codeit.todo.web.dto.request.todo;

import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateTodoRequest(
        @NotNull
        String title,
        @NotNull @FutureOrPresent
        LocalDate startDate,
        @NotNull @FutureOrPresent
        LocalDate endDate,
        String todoLink,
        String imageName,
        String imageEncodedBase64,
        int goalId
) {
        public Todo toEntity(String uploadUrl, Goal goal) {
                return Todo.builder()
                        .todoTitle(this.title)
                        .startDate(this.startDate)
                        .endDate(this.endDate)
                        .todoLink(this.todoLink)
                        .todoPic(uploadUrl)
                        .todoStatus("진행")
                        .createdAt(LocalDateTime.now())
                        .goal(goal)
                        .build();
        }
}
