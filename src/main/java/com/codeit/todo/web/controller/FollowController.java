package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.follow.FollowService;
import com.codeit.todo.web.dto.request.follow.ReadFollowRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoCompleteWithGoalRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.follow.ReadFollowResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;


    @GetMapping
    public Response<Slice<ReadFollowResponse>> getFollows(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute ReadFollowRequest request
            ) {

        int userId = userDetails.getUserId();
        return Response.ok(followService.readFollows(userId, request));
    }
}
