package com.codeit.todo.web.controller;

import com.codeit.todo.service.user.UserService;
import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 받아 로그인 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    @PostMapping(value = "/login")
    public Response login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        String email = loginRequest.email();
        String token= userService.login(loginRequest);
        httpServletResponse.setHeader("token", token);
        return Response.ok( "로그인 성공");
    }

}
