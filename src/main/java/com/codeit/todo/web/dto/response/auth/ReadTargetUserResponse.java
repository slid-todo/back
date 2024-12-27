package com.codeit.todo.web.dto.response.auth;

import com.codeit.todo.domain.User;
import com.codeit.todo.web.dto.response.complete.ReadTargetUserCompleteResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadTargetUserResponse(
        String name,
        String profilePic,
        boolean isFollow,
        List<ReadTargetUserCompleteResponse> completeResponses
) {
    public static ReadTargetUserResponse from(User user, boolean isFollow, List<ReadTargetUserCompleteResponse> completeResponses){
        return ReadTargetUserResponse.builder()
                .name(user.getName())
                .profilePic(user.getProfilePic())
                .isFollow(isFollow)
                .completeResponses(completeResponses)
                .build();
    }
}
