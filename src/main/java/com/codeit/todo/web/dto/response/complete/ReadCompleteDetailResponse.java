package com.codeit.todo.web.dto.response.complete;

import com.codeit.todo.domain.Complete;
import com.codeit.todo.web.dto.response.comment.ReadCommentResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReadCompleteDetailResponse(
        int completeId,
        String completePic,
        String note,
        String completeLink,
        String completeStatus,
        LocalDateTime createdAt,
        LocalDate startDate,
        Boolean likeStatus,
        int likeCount,
        int commentCount,
        String goalName,
        String todoName,
        List<ReadCommentResponse> comments
) {
    public static ReadCompleteDetailResponse from(Complete complete, List<ReadCommentResponse> commentResponses, Boolean likeStatus) {
        return ReadCompleteDetailResponse.builder()
                .completeId(complete.getCompleteId())
                .completePic(complete.getCompletePic())
                .note(complete.getNote())
                .completeLink(complete.getCompleteLink())
                .completeStatus(complete.getCompleteStatus())
                .createdAt(complete.getCreatedAt())
                .startDate(complete.getStartDate())
                .likeCount(complete.getLikes().size())
                .likeStatus(likeStatus)
                .commentCount(commentResponses.size())
                .comments(commentResponses)
                .goalName(complete.getTodo().getGoal().getGoalTitle())
                .todoName(complete.getTodo().getTodoTitle())
                .build();
    }
}
