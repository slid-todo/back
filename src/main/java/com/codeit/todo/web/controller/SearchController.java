package com.codeit.todo.web.controller;

import com.codeit.todo.repository.CustomUserDetails;

import com.codeit.todo.service.search.SearchService;
import com.codeit.todo.web.dto.request.search.ReadSearchRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.search.ReadSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/searches")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "유저 또는 목표 검색", description = "유저 또는 목표를 검색하고 유저와 목표를 함께 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공")
    })
    @GetMapping
    public Response<List<ReadSearchResponse>> getSearch(
            @Valid @RequestBody ReadSearchRequest request
            ){
        return Response.ok(searchService.findUserAndGoal(request));
    }
}
