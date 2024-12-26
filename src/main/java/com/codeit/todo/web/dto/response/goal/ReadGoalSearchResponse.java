package com.codeit.todo.web.dto.response.goal;

import com.codeit.todo.domain.Goal;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadGoalSearchResponse(
        int goalId,
        String goalTitle,
        String color

) {
    public static ReadGoalSearchResponse from(Goal goal){
        return ReadGoalSearchResponse.builder()
                .goalId(goal.getGoalId())
                .goalTitle(goal.getGoalTitle())
                .color(goal.getColor())
                .build();
    }
}
