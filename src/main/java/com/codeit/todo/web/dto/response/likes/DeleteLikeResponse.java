package com.codeit.todo.web.dto.response.likes;

import com.codeit.todo.domain.Complete;
import lombok.Builder;

@Builder
public record DeleteLikeResponse(int completeId) {

    public static DeleteLikeResponse fromEntity(Complete complete) {
        return DeleteLikeResponse.builder()
                .completeId(complete.getCompleteId())
                .build();
    }
}
