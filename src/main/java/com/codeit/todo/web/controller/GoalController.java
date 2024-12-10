package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goals")
public class GoalController {

    private final GoalService goalService;

    @Operation(summary = "목표 조회", description = "유저의 모든 목표 조호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public Response<List<ReadGoalsResponse>> getGoalList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        int userId= customUserDetails.getUserId();
        return Response.ok(goalService.findGoalList(userId));
    }
}
