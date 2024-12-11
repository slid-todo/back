package com.codeit.todo.service.goal;

import com.codeit.todo.repository.CustomUserDetails;
import com.codeit.todo.web.dto.request.goal.CreateGoalRequest;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;

import java.util.List;

public interface GoalService {

    List<ReadGoalsResponse> findGoalList(int userId);

    CreateGoalResponse saveGoal(int userId, CreateGoalRequest request);
}
