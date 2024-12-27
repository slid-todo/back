package com.codeit.todo.web.controller;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.service.user.UserService;
import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.request.auth.SignUpRequest;
import com.codeit.todo.web.dto.request.auth.UpdatePasswordRequest;
import com.codeit.todo.web.dto.request.auth.UpdatePictureRequest;
import com.codeit.todo.web.dto.response.Response;
import com.codeit.todo.web.dto.response.auth.ReadMyPageResponse;
import com.codeit.todo.web.dto.response.auth.UpdatePasswordResponse;
import com.codeit.todo.web.dto.response.auth.UpdatePictureResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {

    private final UserService userService;

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
        String token = userService.login(loginRequest);
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "token");
        httpServletResponse.setHeader("token", token);

        return ResponseEntity.ok( "로그인 성공");
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


    @Operation(
            summary = "프로필 사진 수정",
            description = "유저가 원하는 사진을 골라 프로필 사진을 수정"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PutMapping("/profilepic")
    public Response<UpdatePictureResponse> updateUserProfilePicture(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdatePictureRequest pictureRequest
            ) {
        int userId = userDetails.getUserId();
        return Response.ok(userService.updateProfilePicture(userId, pictureRequest));
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "기존 비밀번호를 확인하고, 새로운 비밀번호로 변경"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공")
    })
    @PutMapping("/password")
    public Response<UpdatePasswordResponse> updateUserPassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdatePasswordRequest passwordRequest
            ) {
        int userId = userDetails.getUserId();
        return Response.ok(userService.updatePassword(userId, passwordRequest));
    }


    @Operation(summary = "마이페이지 가져오기", description = "유저 정보, 팔로워 팔로이 수 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "/mypage")
    public Response<ReadMyPageResponse> getMyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        int userId = customUserDetails.getUserId();
        return Response.ok( userService.findUserInfoAndFollows(userId));
    }

}
