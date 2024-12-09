package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import lombok.Builder;

@Builder
public record CreateTodoResponse(int todoId) {

    public static CreateTodoResponse fromEntity(Todo savedTodo) {
        return CreateTodoResponse.builder()
                .todoId(savedTodo.getTodoId())
                .build();
    }
}
