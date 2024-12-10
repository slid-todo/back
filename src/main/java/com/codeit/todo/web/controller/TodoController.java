package com.codeit.todo.web.controller;

import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.web.dto.request.todo.CreateTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoWithGoalRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.todo.CreateTodoResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodoWithGoalResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosWithGoalsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @Operation(
            summary = "모든 할일 조회",
            description = "할일 목록을 생성일 기준 내림차순으로 정렬하여 조회하는 API입니다. 마지막으로 조회된 할일의 ID(lastTodoId)보다 낮은 ID의 할일을 size만큼 조회합니다. 'lastTodoId'는 가장 최근에 조회된 할일의 ID를 의미하며, 'size'는 한 번에 불러올 할일의 개수를 조정하는 값입니다. 'lastTodoId'가 0이거나 null일 경우(첫 조회) 가장 최근 생성된 할 일 기준으로 정렬됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public Response<Slice<ReadTodosResponse>> getTodoList(
            @Valid @ModelAttribute ReadTodoRequest request
    ) {
        int userId = 1;
        return Response.ok(todoService.findTodoList(userId, request));
    }

    @Operation(
            summary = "목표별 할일 조회",
            description = "목표별 할일 목록을 생성일 기준 내림차순으로 정렬하여 조회하는 API입니다. 목표별 할일에 대한 첫 조회일 경우에만 사용합니다. 'size'는 한 번에 불러올 할일의 개수를 조정하는 값입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/goals")
    public Response<List<ReadTodosWithGoalsResponse>> getTodoWithGoalList(
            @Valid @ModelAttribute ReadTodoWithGoalRequest request
    ) {
        int userId = 1;
        return Response.ok(todoService.findTodoListWithGoals(userId, request));
    }

    @Operation(
            summary = "목표 하나에 해당하는 모든 할일 조회",
            description = "목표 하나에 해당하는 할일 목록을 생성일 기준 내림차순으로 정렬하여 조회하는 API입니다. 마지막으로 조회된 할일의 ID(lastTodoId)보다 낮은 ID의 할일을 size만큼 조회합니다. 'lastTodoId'는 가장 최근에 조회된 할일의 ID를 의미하며, 'size'는 한 번에 불러올 할일의 개수를 조정하는 값입니다. 'lastTodoId'가 0이거나 null일 경우(첫 조회) 가장 최근 생성된 할 일 기준으로 정렬됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/goals/{goalId}")
    public Response<Slice<ReadTodoWithGoalResponse>> getTodoWithGoal(
            @PathVariable int goalId,
            @Valid @ModelAttribute ReadTodoWithGoalRequest request
    ) {
        int userId = 1;
        return Response.ok(todoService.findTodoListWithGoal(userId, goalId, request));
    }

    @Operation(
            summary = "할일 생성",
            description = "할일을 생성하는 API 입니다. 생성된 할 일의 ID가 반환됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping
    public Response<CreateTodoResponse> createTodo(
            @Valid @RequestBody CreateTodoRequest request
    ) {
        int userId = 1;
        return Response.ok(todoService.saveTodo(userId, request));
    }

}
