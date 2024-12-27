package com.codeit.todo.web.dto.response.comment;

import com.codeit.todo.domain.Comment;
import lombok.Builder;

@Builder
public record UpdateCommentResponse(int commentId) {

    public static UpdateCommentResponse fromEntity(Comment comment) {
        return UpdateCommentResponse.builder()
                .commentId(comment.getCommentId())
                .build();
    }

}
