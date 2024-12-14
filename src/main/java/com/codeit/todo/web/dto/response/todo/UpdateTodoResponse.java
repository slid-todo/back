package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import lombok.Builder;

@Builder
public record UpdateTodoResponse(int todoId) {
    public static UpdateTodoResponse fromEntity(Todo todo) {
        return UpdateTodoResponse.builder()
                .todoId(todo.getTodoId())
                .build();
    }
}
