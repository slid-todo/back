package com.codeit.todo.web.dto.request.auth;


import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SignUpRequest(
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        String passwordCheck) {

    public User toEntity(String encodedPassword, String profilePic) {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(encodedPassword)
                .profilePic(profilePic)
                .userStatus("가입")
                .build();
    }

}
