package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.web.dto.request.goal.CreateGoalRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goals")
public class GoalController {

    private final GoalService goalService;

    @Operation(summary = "목표 조회", description = "유저의 모든 목표 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public Response<List<ReadGoalsResponse>> getGoalList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        int userId= customUserDetails.getUserId();
        return Response.ok(goalService.findGoalList(userId));
    }

    @Operation(summary = "목표 생성",
            description = "새로운 목표 생성 API, 생성된 목표의 ID 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "목표 생성 성공")
    })
    @PostMapping
    public Response<CreateGoalResponse> createGoal(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody CreateGoalRequest request
    ) {
        int userId= customUserDetails.getUserId();
        return Response.ok(goalService.saveGoal(userId, request));
    }
}
