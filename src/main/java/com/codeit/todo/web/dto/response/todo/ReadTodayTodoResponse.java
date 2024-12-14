package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Todo;
import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadTodayTodoResponse(int todoId, String todoTitle, ReadCompleteResponse complete) {
    public static ReadTodayTodoResponse from(Todo todo, ReadCompleteResponse todayComplete) {
        return ReadTodayTodoResponse.builder()
                .todoId(todo.getTodoId())
                .todoTitle(todo.getTodoTitle())
                .complete(todayComplete)
                .build();
    }
}
