package com.codeit.todo.web.dto.response.follow;

import lombok.Builder;

@Builder
public record DeleteFollowResponse(int followerId, int followeeId) {
    public static DeleteFollowResponse from(int followerId, int followeeId) {
        return DeleteFollowResponse.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .build();
    }
}
