package com.codeit.todo.service.goal;

import com.codeit.todo.web.dto.request.goal.UpdateGoalRequest;
import com.codeit.todo.web.dto.request.goal.CreateGoalRequest;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import com.codeit.todo.web.dto.response.goal.UpdateGoalResponse;

import java.util.List;

public interface GoalService {

    List<ReadGoalsResponse> findGoalList(int userId);

    CreateGoalResponse saveGoal(int userId, CreateGoalRequest request);

    UpdateGoalResponse updateGoal(int userId, int goalId, UpdateGoalRequest request);
}
