package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.follow.FollowService;
import com.codeit.todo.web.dto.request.follow.ReadFollowRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.follow.CreateFollowResponse;
import com.codeit.todo.web.dto.response.follow.DeleteFollowResponse;
import com.codeit.todo.web.dto.response.follow.ReadFollowResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;


    @Operation(summary = "팔로우의 최근 등록한 인증글 목록 조회", description = "팔로우의 최근 등록한 인증글 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public Response<Slice<ReadFollowResponse>> getFollows(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute ReadFollowRequest request
            ) {

        int userId = userDetails.getUserId();
        return Response.ok(followService.readFollows(userId, request));
    }

    @Operation(summary = "팔로우 등록", description = "팔로우 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping("{followerId}")
    public Response<CreateFollowResponse> createFollow(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int followerId
    ) {
        int followeeId = userDetails.getUserId();
        return Response.ok(followService.registerFollow(followerId, followeeId));
    }

    @Operation(summary = "팔로우 취소", description = "팔로우 취소 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @DeleteMapping("{followerId}")
    public Response<DeleteFollowResponse> cancelFollow(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int followerId
    ) {
        int followeeId = userDetails.getUserId();
        return Response.ok(followService.cancelFollow(followerId, followeeId));
    }
}
