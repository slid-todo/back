package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.web.dto.request.goal.CreateGoalRequest;
import com.codeit.todo.web.dto.request.goal.UpdateGoalRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoCompleteWithGoalRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.goal.DeleteGoalResponse;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import com.codeit.todo.web.dto.response.goal.UpdateGoalResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosWithGoalsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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



    @Operation(summary = "목표 수정",
            description = "기존 목표 제목 수정 API, 수정된 목표의 ID 반환")
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


    @Operation(summary = "목표 삭제",
            description = "목표 삭제 API, 삭제된 목표의 ID 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "목표 삭제 성공")
    })
    @DeleteMapping ("/{goalId}")
    public Response<DeleteGoalResponse> deleteGoal(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable int goalId
    ) {
        int userId= customUserDetails.getUserId();
        return Response.ok(goalService.deleteGoal(userId, goalId));
    }

    @Operation(
            summary = "목표와 할 일, 인증 상세조회",
            description = "종료된 할 일 또는 인증도 포함해서 목표, 할 일, 인증을 모두 불러옵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/all")
    public Response<Slice<ReadTodosWithGoalsResponse>> getGoalsDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute ReadTodoCompleteWithGoalRequest request
            ) {
        int userId = userDetails.getUserId();
        return Response.ok(goalService.findAllGoals(userId, request));
    }

}
