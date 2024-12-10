package com.codeit.todo.service.goal;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.web.dto.response.goal.DeleteGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;

import java.util.List;

public interface GoalService {

    List<ReadGoalsResponse> findGoalList(int userId);

    DeleteGoalResponse deleteGoal(int userId, int goalId);
}
