package com.codeit.todo.web.dto.response.auth;

import com.codeit.todo.domain.User;
import lombok.Builder;

@Builder
public record ReadUserResponse(
        String name,
        String email
) {
    public static ReadUserResponse from(User user){
        return ReadUserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
