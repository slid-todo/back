package com.codeit.todo.web.dto.response.auth;

import com.codeit.todo.domain.User;
import lombok.Builder;

@Builder
public record ReadMyPageResponse(
        int followerCount,
        int followeeCount
) {
    public static ReadMyPageResponse from(int followerCount, int followeeCount){
        return ReadMyPageResponse.builder()
                .followerCount(followerCount)
                .followeeCount(followeeCount)
                .build();
    }
}
