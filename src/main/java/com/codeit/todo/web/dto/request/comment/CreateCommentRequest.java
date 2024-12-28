package com.codeit.todo.web.dto.request.comment;

import com.codeit.todo.domain.*;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;

public record CreateCommentRequest(
        int completeId,
        @NotNull
        String content
) {
        public Comment toEntity(String content, User user, Complete complete) {
                return Comment.builder()
                        .content(content)
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .complete(complete)
                        .build();
        }
}
