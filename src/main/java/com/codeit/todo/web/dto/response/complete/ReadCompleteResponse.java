package com.codeit.todo.web.dto.response.complete;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReadCompleteResponse(
        int completeId,
        String completePic,
        String note,
        String completeLink,
        String completeFile,
        LocalDateTime createdAt,
        LocalDate completedDate
) {
}
