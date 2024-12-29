package com.codeit.todo.web.dto.response.auth;

import lombok.Builder;

@Builder
public record UpdateUserStatusResponse(int userId) {

    public static UpdateUserStatusResponse from(int userId) {
        return UpdateUserStatusResponse.builder()
                .userId(userId)
                .build();
    }

}
