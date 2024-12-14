package com.codeit.todo.web.dto.response.complete;

import com.codeit.todo.domain.Complete;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReadCompleteResponse(
        int completeId,
        String completePic,
        String note,
        String completeLink,
        String completeStatus,
        LocalDateTime createdAt,
        LocalDate startDate
) {
    public static ReadCompleteResponse from(Complete complete) {
        return ReadCompleteResponse.builder()
                .completeId(complete.getCompleteId())
                .completePic(complete.getCompletePic())
                .note(complete.getNote())
                .completeLink(complete.getCompleteLink())
                .completeStatus(complete.getCompleteStatus())
                .createdAt(complete.getCreatedAt())
                .startDate(complete.getStartDate())
                .build();
    }
}
