package com.codeit.todo.web.dto.response.todo;

import com.codeit.todo.domain.Goal;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadTodosWithGoalsResponse(
        int goalId,
        String goalTitle,
        String goalColor,
        double progress,
        List<ReadTodosResponse> todos
) {
    public static ReadTodosWithGoalsResponse from(Goal goal, List<ReadTodosResponse> responses, double goalProgress) {
        return ReadTodosWithGoalsResponse.builder()
                .goalId(goal.getGoalId())
                .goalTitle(goal.getGoalTitle())
                .goalColor(goal.getColor())
                .progress(goalProgress)
                .todos(responses)
                .build();
    }
}
