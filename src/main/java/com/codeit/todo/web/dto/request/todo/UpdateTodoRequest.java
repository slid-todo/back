package com.codeit.todo.web.dto.request.todo;

import java.time.LocalDate;

public record UpdateTodoRequest(
        String todoTitle,
        LocalDate startDate,
        LocalDate endDate,
        String todoLink,
        String todoStatus,
        String todoPicBase64,
        String picName
) {
}
