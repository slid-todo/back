package com.codeit.todo.service.goal.impl;


import com.codeit.todo.common.exception.goal.GoalNotFoundException;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import com.codeit.todo.domain.User;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.TodoRepository;
import com.codeit.todo.repository.UserRepository;
import com.codeit.todo.service.goal.GoalService;
import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.service.todo.impl.TodoServiceImpl;
import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import com.codeit.todo.web.dto.response.goal.DeleteGoalResponse;
import com.codeit.todo.web.dto.request.goal.UpdateGoalRequest;
import com.codeit.todo.web.dto.request.goal.CreateGoalRequest;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import com.codeit.todo.web.dto.response.goal.ReadGoalsResponse;
import com.codeit.todo.web.dto.response.goal.UpdateGoalResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosWithGoalsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final TodoRepository todoRepository;
    private final CompleteRepository completeRepository;
    private final TodoServiceImpl todoServiceImpl;


    @Override
    public List<ReadGoalsResponse> findGoalList(int userId) {
        List<Goal> goals= goalRepository.findByUser_UserId(userId);

        if (goals.isEmpty()) {
            throw new GoalNotFoundException("0");
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

    @Transactional
    @Override
    public CreateGoalResponse saveGoal(int userId, CreateGoalRequest request) {
        String[] colors= {"#FFAB76", "#F49696", "#B18AE0", "#A8D8F0", "#FFEC8B"};
        Random random = new Random();
        String color = colors[random.nextInt(colors.length)];


        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(String.valueOf(userId), "User"));

        Goal goal= request.toEntity(color, user);
        Goal savedGoal = goalRepository.save(goal);

        return CreateGoalResponse.fromEntity(savedGoal);
    }

    @Override
    public UpdateGoalResponse updateGoal(int userId, int goalId, UpdateGoalRequest request) {
        Goal goal = goalRepository.findByGoalIdAndUser_UserId(goalId, userId)
                .orElseThrow(()-> new GoalNotFoundException(String.valueOf(goalId)));

        goal.update(request.title());
        return new UpdateGoalResponse(goalId);
    }

    @Override
    @Transactional
    public DeleteGoalResponse deleteGoal(int userId, int goalId) {

        Goal goal = goalRepository.findByGoalIdAndUser_UserId(goalId, userId)
                .orElseThrow(()-> new GoalNotFoundException(String.valueOf(goalId)));

        goalRepository.delete(goal);
        return new DeleteGoalResponse(goalId);
    }

    @Override
    public List<ReadTodosWithGoalsResponse> findAllGoals(int userId) {
        List<Goal> goals = goalRepository.findByUser_UserId(userId);
        List<ReadTodosWithGoalsResponse> goalsResponses = goals.stream()
                .map(goal -> {
                    List<Todo> todos = todoRepository.findByGoal_GoalId(goal.getGoalId());

                    List<ReadTodosResponse> todosResponses = todoServiceImpl.makeTodosResponses(todos);

                    double goalProgress = todoServiceImpl.calculateGoalProgress(todos);

                    return ReadTodosWithGoalsResponse.from(goal, todosResponses, goalProgress);
                }).toList();

        return goalsResponses;
    }
}
