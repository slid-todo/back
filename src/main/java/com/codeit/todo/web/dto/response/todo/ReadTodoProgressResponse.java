package com.codeit.todo.web.dto.response.todo;

import lombok.Builder;

@Builder
public record ReadTodoProgressResponse(double progress) {
    public static ReadTodoProgressResponse from(double progress) {
        return ReadTodoProgressResponse.builder()
                .progress(progress)
                .build();
    }
}
