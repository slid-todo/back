package com.codeit.todo.service.comment.impl;

import com.codeit.todo.common.exception.complete.CompleteNotFoundException;
import com.codeit.todo.domain.Comment;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.User;
import com.codeit.todo.repository.CommentRepository;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.service.comment.CommentService;
import com.codeit.todo.service.user.UserService;
import com.codeit.todo.service.user.impl.UserServiceImpl;
import com.codeit.todo.web.dto.request.comment.CreateCommentRequest;
import com.codeit.todo.web.dto.response.comment.CreateCommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserServiceImpl userServiceImpl;
    private final CompleteRepository completeRepository;
    private final CommentRepository commentRepository;

    @Override
    public CreateCommentResponse saveComment(int userId, CreateCommentRequest request) {

        User user = userServiceImpl.getUser(userId);
        Complete complete = completeRepository.findById(request.completeId())
                .orElseThrow(()-> new CompleteNotFoundException(String.valueOf(request.completeId())));

        Comment comment = request.toEntity(request.content(), user, complete);
        Comment savedComment = commentRepository.save(comment);
        return CreateCommentResponse.fromEntity(savedComment);
    }
}
