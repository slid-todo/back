package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReadTodosResponse(
        int todoId,
        String todoTitle,
        LocalDate startDate,
        LocalDate endDate,
        String todoStatus,
        String todoLink,
        String todoPic,
        LocalDateTime createdAt,
        List<ReadCompleteResponse> completes
) {
}
