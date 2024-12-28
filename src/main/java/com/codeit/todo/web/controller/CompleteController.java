package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.complete.CompleteService;
import com.codeit.todo.web.dto.request.complete.UpdateCompleteRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.complete.ReadCompleteDetailResponse;
import com.codeit.todo.web.dto.response.complete.UpdateCompleteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/completes")
public class CompleteController {

    private final CompleteService completeService;

    @Operation(
            summary = "인증 및 노트 수정",
            description = "인증 및 노트를 수정하는 API 입니다. 할 일에 대해 인증할 때 사용되는 API입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PutMapping("{completeId}")
    public Response<UpdateCompleteResponse> updateComplete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateCompleteRequest request,
            @PathVariable int completeId
            ) {
        int userId = userDetails.getUserId();
        return Response.ok(completeService.updateCompleteInfo(userId, completeId, request));
    }

    @Operation(
            summary = "인증 및 노트 상세 조회",
            description = "인증 및 노트 상세조회하는  API 입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("{completeId}")
    public Response<ReadCompleteDetailResponse> readComplete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int completeId
    ) {
        int userId = userDetails.getUserId();
        return Response.ok(completeService.readComplete(completeId, userId));
    }
}
