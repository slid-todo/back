package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.likes.LikesService;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.likes.CreateLikeResponse;
import com.codeit.todo.web.dto.response.likes.DeleteLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/completes/{completeId}/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    public Response<CreateLikeResponse> createLikes(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int completeId
    ) {
        int userId = userDetails.getUserId();
        return Response.ok(likesService.createLikes(userId, completeId));
    }

    @DeleteMapping
    public Response<DeleteLikeResponse> deleteLikes(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int completeId
    ) {
        int userId = userDetails.getUserId();
        return Response.ok(likesService.deleteLikes(userId, completeId));
    }
}
