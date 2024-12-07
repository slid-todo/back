package com.codeit.todo.web.controller;

import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.todo.ReadTodosResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @Operation(
            summary = "모든 할일 조회",
            description = "할일 목록을 생성일 기준 내림차순으로 정렬하여 조회합니다. 마지막으로 조회된 할일의 ID(lastTodoId)보다 낮은 ID의 할일을 size만큼 조회합니다. 'lastTodoId'는 가장 최근에 조회된 할일의 ID를 의미하며, 'size'는 한 번에 불러올 할일의 개수를 조정하는 값입니다. 'lastTodoId'가 0이거나 null일 경우(첫 조회) 가장 최근 생성된 할 일 기준으로 정렬됩니다."
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

}
