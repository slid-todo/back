package com.codeit.todo.web.dto.response.search;

import com.codeit.todo.domain.User;
import com.codeit.todo.web.dto.response.goal.ReadGoalSearchResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadSearchResponse (
        int userId,
        String name,
        String profilePic,

        List<ReadGoalSearchResponse> goals
){
    public static ReadSearchResponse from(User user, List<ReadGoalSearchResponse> responses) {
        return ReadSearchResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .profilePic(user.getProfilePic())
                .goals(responses)
                .build();
    }
}
