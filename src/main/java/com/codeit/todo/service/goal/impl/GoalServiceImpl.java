package com.codeit.todo.service.goal.impl;


import com.codeit.todo.common.exception.goal.GoalNotFoundException;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.web.dto.request.goal.UpdateGoalRequest;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import com.codeit.todo.web.dto.response.goal.UpdateGoalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;



    @Override
    public List<ReadGoalsResponse> findGoalList(int userId) {
        List<Goal> goals= goalRepository.findByUser_UserId(userId);

        if (goals.isEmpty()) {
            throw new GoalNotFoundException("0", "Goal");
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
    public UpdateGoalResponse updateGoal(int userId, int goalId, UpdateGoalRequest request) {
        Goal goal = goalRepository.findByGoalIdAndUser_UserId(goalId, userId)
                .orElseThrow(()-> new GoalNotFoundException(String.valueOf(goalId), "Goal"));

        goal.update(request.title());
        return new UpdateGoalResponse(goalId);
    }
}
