package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Goal;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadTodosWithGoalsResponse(
        int goalId,
        String goalTitle,
        String goalColor,
        List<ReadTodosResponse> todos
) {
    public static ReadTodosWithGoalsResponse from(Goal goal, List<ReadTodosResponse> responses) {
        return ReadTodosWithGoalsResponse.builder()
                .goalId(goal.getGoalId())
                .goalTitle(goal.getGoalTitle())
                .goalColor(goal.getColor())
                .todos(responses)
                .build();
    }
}
