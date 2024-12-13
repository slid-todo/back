package com.codeit.todo.web.controller;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.service.user.UserService;
import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.request.auth.SignUpRequest;
import com.codeit.todo.web.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호로 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @PostMapping(value = "/signup")
    public Response signUp(@RequestBody SignUpRequest signUpRequest){
        return Response.ok( userService.signUpUser(signUpRequest));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 받아 로그인 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        userService.login(loginRequest);
        Cookie cookie = jwtTokenProvider.createCookie(loginRequest.email());

        ResponseCookie responseCookie = ResponseCookie.from("token", cookie.getValue())
            .secure(false)
            .path("/")
            .httpOnly(true)
            .maxAge(cookie.getMaxAge())
            .sameSite("None")
            .build();

        httpServletResponse.addHeader("Set-Cookie", responseCookie.toString());

        return ResponseEntity.ok( "로그인 성공");
    }

}
