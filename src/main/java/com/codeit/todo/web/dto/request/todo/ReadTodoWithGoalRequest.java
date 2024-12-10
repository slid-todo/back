package com.codeit.todo.web.dto.request.todo;

import jakarta.validation.constraints.Min;

public record ReadTodoWithGoalRequest(
        Integer lastTodoId,
        @Min(3)
        int size
) {
}
