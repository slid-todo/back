package com.codeit.todo.web.dto.response.todo;

import java.util.List;

public record ReadTodosWithGoalsResponse(
        int goalId,
        String goalTitle,
        List<ReadTodosResponse> todos
) {
}
