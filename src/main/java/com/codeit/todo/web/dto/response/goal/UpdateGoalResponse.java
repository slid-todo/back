package com.codeit.todo.web.dto.response.goal;


import lombok.Builder;

@Builder
public record UpdateGoalResponse( int goalId) {

    public static UpdateGoalResponse fromEntity(int goalId) {
        return UpdateGoalResponse.builder()
                .goalId(goalId)
                .build();
    }

}
