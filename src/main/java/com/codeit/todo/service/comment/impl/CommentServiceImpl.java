package com.codeit.todo.service.comment.impl;

import com.codeit.todo.common.exception.auth.AuthorizationDeniedException;
import com.codeit.todo.common.exception.comment.CommentNotFoundException;
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
import com.codeit.todo.web.dto.request.comment.UpdateCommentRequest;
import com.codeit.todo.web.dto.response.comment.CreateCommentResponse;
import com.codeit.todo.web.dto.response.comment.UpdateCommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserServiceImpl userServiceImpl;
    private final CompleteRepository completeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public CreateCommentResponse saveComment(int userId, CreateCommentRequest request) {

        User user = userServiceImpl.getUser(userId);
        Complete complete = completeRepository.findById(request.completeId())
                .orElseThrow(()-> new CompleteNotFoundException(String.valueOf(request.completeId())));

        Comment comment = request.toEntity(request.content(), user, complete);
        Comment savedComment = commentRepository.save(comment);
        return CreateCommentResponse.fromEntity(savedComment);
    }

    @Transactional
    @Override
    public UpdateCommentResponse updateComment(int userId, int commentId, UpdateCommentRequest request) {
        Comment comment = getComment(userId, commentId);
        comment.update(request.content());

        return UpdateCommentResponse.fromEntity(comment);
    }

    private Comment getComment(int userId, int commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException(String.valueOf(commentId)));

        if(comment.getUser().getUserId() != userId){
            log.error("댓글 수정 작업 거부됨. 요청 유저 ID : {}", userId);
            throw new AuthorizationDeniedException("댓글 수정에 대한 권한이 없습니다.");
        }

        return comment;
    }
}
