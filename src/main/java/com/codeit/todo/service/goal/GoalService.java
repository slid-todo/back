package com.codeit.todo.service.goal;

import com.codeit.todo.web.dto.response.goal.DeleteGoalResponse;
import com.codeit.todo.web.dto.request.goal.UpdateGoalRequest;
import com.codeit.todo.web.dto.request.goal.CreateGoalRequest;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import com.codeit.todo.web.dto.response.goal.UpdateGoalResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosWithGoalsResponse;

import java.util.List;

public interface GoalService {

    List<ReadGoalsResponse> findGoalList(int userId);

    CreateGoalResponse saveGoal(int userId, CreateGoalRequest request);

    UpdateGoalResponse updateGoal(int userId, int goalId, UpdateGoalRequest request);

    DeleteGoalResponse deleteGoal(int userId, int goalId);


    List<ReadTodosWithGoalsResponse> findAllGoals(int userId);
}
