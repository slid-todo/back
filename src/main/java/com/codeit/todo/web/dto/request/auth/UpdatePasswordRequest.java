package com.codeit.todo.web.dto.request.auth;

import jakarta.validation.constraints.NotNull;

public record UpdatePasswordRequest(
        @NotNull
        String currentPassword,
        @NotNull
        String newPassword,
        @NotNull
        String newPasswordCheck
){
}
