package com.codeit.todo.web.dto.response.todo;

import lombok.Builder;

@Builder
public record DeleteTodoResponse(int todoId) {
    public static DeleteTodoResponse from(int todoId) {
        return DeleteTodoResponse.builder()
                .todoId(todoId)
                .build();
    }
}
