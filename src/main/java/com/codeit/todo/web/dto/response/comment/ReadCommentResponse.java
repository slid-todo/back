package com.codeit.todo.web.dto.response.comment;

import com.codeit.todo.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadCommentResponse(
        int commentId,
        String content,
        String profileImage,
        LocalDateTime createdAt
) {
    public static ReadCommentResponse fromEntity(Comment comment) {
        return ReadCommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .profileImage(comment.getUser().getProfilePic())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
