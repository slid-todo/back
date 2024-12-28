package com.codeit.todo.web.dto.request.follow;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record ReadFollowRequest(
        Integer lastCompleteId,
        @Min(1)
        int size
) {
}
