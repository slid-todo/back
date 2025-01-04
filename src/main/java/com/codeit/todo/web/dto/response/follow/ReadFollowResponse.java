package com.codeit.todo.web.dto.response.follow;

import com.codeit.todo.domain.Complete;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadFollowResponse(
        int completeId,
        int userId,
        String completePic,
        String completeContent,
        String profilePic,
        String username,
        LocalDateTime createdAt,
        Boolean likeStatus,
        int likeCount,
        int commentCount
) {


    public static ReadFollowResponse from(Complete complete, Boolean likeStatus) {
        return ReadFollowResponse.builder()
                .completeId(complete.getCompleteId())
                .userId(complete.getTodo().getGoal().getUser().getUserId())
                .completePic(complete.getCompletePic())
                .completeContent(complete.getNote())
                .profilePic(complete.getTodo().getGoal().getUser().getProfilePic())
                .username(complete.getTodo().getGoal().getUser().getName())
                .createdAt(complete.getCreatedAt())
                .likeStatus(likeStatus)
                .likeCount(complete.getLikes().size())
                .commentCount(complete.getComments().size())
                .build();
    }
}
