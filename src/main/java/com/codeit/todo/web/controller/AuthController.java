package com.codeit.todo.web.controller;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.user.UserService;
import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.request.auth.SignUpRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.auth.ReadUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {

    private final UserService userService;
    private static final int COOKIE_VALID_SECONDS = 60*60*24; //24시간


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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String token = userService.login(loginRequest);
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(COOKIE_VALID_SECONDS)
                .sameSite("None")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("로그인 성공");
    }

    @Operation(summary = "유저 정보 가져오기", description = "유저의 이름, 이메일 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "/user")
    public Response getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        int userId = customUserDetails.getUserId();
        return Response.ok( userService.findUserInfo(userId) );
    }
}
