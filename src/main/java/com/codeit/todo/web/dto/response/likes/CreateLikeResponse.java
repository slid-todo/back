package com.codeit.todo.web.dto.response.likes;

import com.codeit.todo.domain.Likes;
import lombok.Builder;

@Builder
public record CreateLikeResponse(int completeId, int likesId) {

    public static CreateLikeResponse fromEntity(Likes likes) {
        return CreateLikeResponse.builder()
                .completeId(likes.getComplete().getCompleteId())
                .likesId(likes.getLikesId())
                .build();
    }
}
