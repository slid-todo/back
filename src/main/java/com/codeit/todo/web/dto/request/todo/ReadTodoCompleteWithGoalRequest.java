package com.codeit.todo.web.dto.request.todo;

import jakarta.validation.constraints.Min;

public record ReadTodoCompleteWithGoalRequest(
        Integer lastGoalId,
        @Min(3)
        int size
) {
}
