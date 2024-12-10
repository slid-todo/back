package com.codeit.todo.service.goal.impl;


import com.codeit.todo.common.exception.goal.GoalNotFoundException;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.TodoRepository;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.web.dto.response.goal.DeleteGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final TodoRepository todoRepository;
    private final CompleteRepository completeRepository;



    @Override
    public List<ReadGoalsResponse> findGoalList(int userId) {
        List<Goal> goals= goalRepository.findByUser_UserId(userId);

        if (goals.isEmpty()) {
            throw new GoalNotFoundException("goalId", "Goal");
        }

        return goals.stream()
                .map(goal-> {
                            return ReadGoalsResponse.builder()
                                    .goalId(goal.getGoalId())
                                    .goalTitle(goal.getGoalTitle())
                                    .color(goal.getColor())
                                    .createdAt(goal.getCreatedAt())
                                    .build();
                        })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DeleteGoalResponse deleteGoal(int userId, int goalId) {

        Goal goal = goalRepository.findByGoalIdAndUser_UserId(goalId, userId)
                .orElseThrow(()-> new GoalNotFoundException(String.valueOf(goalId), "Goal"));

        goalRepository.delete(goal);
        return new DeleteGoalResponse(goalId);
    }
}
