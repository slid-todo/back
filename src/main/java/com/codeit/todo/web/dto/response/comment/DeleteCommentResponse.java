package com.codeit.todo.web.dto.response.comment;

import com.codeit.todo.domain.Comment;
import lombok.Builder;

@Builder
public record DeleteCommentResponse(int commentId) {

    public static DeleteCommentResponse from(int commentId) {
        return DeleteCommentResponse.builder()
                .commentId(commentId)
                .build();
    }

}
