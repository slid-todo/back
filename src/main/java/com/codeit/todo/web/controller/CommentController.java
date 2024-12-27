package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.comment.CommentService;
import com.codeit.todo.web.dto.request.comment.CreateCommentRequest;
import com.codeit.todo.web.dto.request.comment.UpdateCommentRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.comment.CreateCommentResponse;
import com.codeit.todo.web.dto.response.comment.UpdateCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "새로운 댓글 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공")
    })
    @PostMapping
    public Response<CreateCommentResponse> createComment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody CreateCommentRequest request
            ){
        int userId = customUserDetails.getUserId();
        return Response.ok(commentService.saveComment(userId, request));
    }

    @Operation(summary = "댓글 수정", description = "댓글 내용 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PutMapping("{commentId}")
    public Response<UpdateCommentResponse> updateComment(
            @PathVariable int commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody UpdateCommentRequest request
    ){
        int userId = customUserDetails.getUserId();
        return Response.ok(commentService.updateComment(userId, commentId, request));
    }
}
