package com.codeit.todo.web.dto.response.comment;

import com.codeit.todo.domain.Comment;
import com.codeit.todo.domain.Goal;
import lombok.Builder;

@Builder
public record CreateCommentResponse(int commentId ) {

    public static CreateCommentResponse fromEntity(Comment savedComment) {
        return CreateCommentResponse.builder()
                .commentId(savedComment.getCommentId())
                .build();
    }

}
