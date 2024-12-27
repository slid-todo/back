package com.codeit.todo.service.comment;

import com.codeit.todo.web.dto.request.comment.CreateCommentRequest;
import com.codeit.todo.web.dto.response.comment.CreateCommentResponse;

public interface CommentService {
    CreateCommentResponse saveComment(int userId, CreateCommentRequest request);
}
