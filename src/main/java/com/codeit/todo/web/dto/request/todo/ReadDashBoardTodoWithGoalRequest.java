package com.codeit.todo.web.dto.request.todo;

import jakarta.validation.constraints.Min;

public record ReadDashBoardTodoWithGoalRequest(
        Integer lastGoalId,
        @Min(3)
        int size
) {
}
