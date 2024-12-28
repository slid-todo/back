package com.codeit.todo.web.dto.response.follow;

import com.codeit.todo.domain.Complete;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadFollowResponse(
        int completeId,
        String completePic,
        LocalDateTime createdAt,
        Boolean likeStatus,
        int likeCount,
        int commentCount
) {


    public static ReadFollowResponse from(Complete complete, Boolean likeStatus) {
        return ReadFollowResponse.builder()
                .completeId(complete.getCompleteId())
                .completePic(complete.getCompletePic())
                .createdAt(complete.getCreatedAt())
                .likeStatus(likeStatus)
                .likeCount(complete.getLikes().size())
                .commentCount(complete.getComments().size())
                .build();
    }
}
