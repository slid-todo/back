package com.codeit.todo.web.dto.request.goal;

import jakarta.validation.constraints.NotNull;

public record UpdateGoalRequest (
        @NotNull
        String title
){
}
