package com.codeit.todo.web.dto.response.follow;

import com.codeit.todo.domain.Follow;
import lombok.Builder;

@Builder
public record CreateFollowResponse(
        int followId
) {
    public static CreateFollowResponse fromEntity(Follow follow) {
        return CreateFollowResponse.builder()
                .followId(follow.getFollowId())
                .build();
    }
}
