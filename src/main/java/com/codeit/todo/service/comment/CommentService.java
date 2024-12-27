package com.codeit.todo.service.comment;

import com.codeit.todo.web.dto.request.comment.CreateCommentRequest;
import com.codeit.todo.web.dto.request.comment.UpdateCommentRequest;
import com.codeit.todo.web.dto.response.comment.CreateCommentResponse;
import com.codeit.todo.web.dto.response.comment.UpdateCommentResponse;

public interface CommentService {
    CreateCommentResponse saveComment(int userId, CreateCommentRequest request);

    UpdateCommentResponse updateComment(int userId, int commentId, UpdateCommentRequest request);
}
