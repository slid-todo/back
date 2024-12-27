package com.codeit.todo.web.dto.response.complete;

import com.codeit.todo.domain.Complete;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReadTargetUserCompleteResponse(
        int completeId,
        String completePic
) {
    public static ReadTargetUserCompleteResponse from(Complete complete) {
        return ReadTargetUserCompleteResponse.builder()
                .completeId(complete.getCompleteId())
                .completePic(complete.getCompletePic())
                .build();
    }
}
