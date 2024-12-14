package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadTodayTodoResponse(int todoId, String todoTitle, List<ReadCompleteResponse> complete) {
    public static ReadTodayTodoResponse from(Todo todo, List<ReadCompleteResponse> todayCompletes) {
        return ReadTodayTodoResponse.builder()
                .todoId(todo.getTodoId())
                .todoTitle(todo.getTodoTitle())
                .complete(todayCompletes)
                .build();
    }
}
