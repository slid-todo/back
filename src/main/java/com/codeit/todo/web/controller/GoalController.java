package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.web.dto.request.goal.UpdateGoalRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import com.codeit.todo.web.dto.response.goal.UpdateGoalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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


    @Transactional
    @Operation(summary = "목표 수정",
            description = "기존 목표 제목 수정 API, 생성된 목표의 ID 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "목표 수정 성공")
    })
    @PutMapping("/{goalId}")
    public Response<UpdateGoalResponse> updateGoal(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody UpdateGoalRequest request,
            @PathVariable int goalId
    ) {
        int userId= customUserDetails.getUserId();
        return Response.ok(goalService.updateGoal(userId, goalId, request));
    }
}
