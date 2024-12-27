package com.codeit.todo.web.dto.response.auth;

import com.codeit.todo.domain.User;
import lombok.Builder;

@Builder
public record ReadMyPageResponse(
        String name,
        String email,
        String profilePic,

        int followerCount,
        int followeeCount
) {
    public static ReadMyPageResponse from(User user, int followerCount, int followeeCount){
        return ReadMyPageResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profilePic(user.getProfilePic())
                .followerCount(followerCount)
                .followeeCount(followeeCount)
                .build();
    }
}
