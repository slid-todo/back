package com.codeit.todo.web.dto.request.comment;

import com.codeit.todo.domain.Comment;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateCommentRequest( @NotNull String content) {}
