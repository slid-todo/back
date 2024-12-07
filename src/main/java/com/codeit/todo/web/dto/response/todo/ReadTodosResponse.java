package com.codeit.todo.web.dto.response.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReadTodosResponse(
        int todoId,
        String todoTitle,
        LocalDate startDate,
        LocalDate endDate,
        Boolean todoStatus,
        String todoLink,
        String todoPic,
        LocalDateTime createdAt
) {
}
