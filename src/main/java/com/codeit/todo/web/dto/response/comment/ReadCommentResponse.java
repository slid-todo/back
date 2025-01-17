package com.codeit.todo.web.dto.response.comment;

import com.codeit.todo.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadCommentResponse(
        int commentId,
        int userId,
        String content,
        String userName,
        String profileImage,
        LocalDateTime createdAt
) {
    public static ReadCommentResponse fromEntity(Comment comment) {
        return ReadCommentResponse.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .content(comment.getContent())
                .userName(comment.getUser().getName())
                .profileImage(comment.getUser().getProfilePic())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
