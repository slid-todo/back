package com.codeit.todo.web.dto.response.goal;

import com.codeit.todo.domain.Goal;
import lombok.Builder;

@Builder
public record CreateGoalResponse( int goalId ) {

    public static CreateGoalResponse fromEntity(Goal savedGoal) {
        return CreateGoalResponse.builder()
                .goalId(savedGoal.getGoalId())
                .build();
    }

}
