package com.codeit.todo.service.todo.impl;

import com.codeit.todo.common.exception.EntityNotFoundException;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.TodoRepository;
import com.codeit.todo.service.storage.StorageService;
import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.web.dto.request.todo.CreateTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoWithGoalRequest;
import com.codeit.todo.web.dto.response.todo.CreateTodoResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosWithGoalsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final GoalRepository goalRepository;

    private final StorageService storageService;

    @Transactional(readOnly = true)
    @Override
    public Slice<ReadTodosResponse> findTodoList(int userId, ReadTodoRequest request) {
        List<Goal> goals = goalRepository.findByUser_UserId(userId);
        List<Integer> goalIds = goals.stream()
                .map(Goal::getGoalId)
                .toList();

        Pageable pageable = PageRequest.of(0, request.size());

        Slice<Todo> todos;
        if (Objects.isNull(request.lastTodoId()) || request.lastTodoId() <= 0) {
            todos = todoRepository.findByGoal_GoalIdInOrderByTodoIdDesc(goalIds, pageable);
        }

        else {
            todos = todoRepository.findByGoal_GoalIdInAndTodoIdLessThanOrderByTodoIdDesc(
                    goalIds, request.lastTodoId(), pageable
            );
        }

        List<ReadTodosResponse> responseList = todos.getContent().stream()
                .map(todo -> new ReadTodosResponse(
                        todo.getTodoId(),
                        todo.getTodoTitle(),
                        todo.getStartDate(),
                        todo.getEndDate(),
                        todo.getTodoStatus(),
                        todo.getTodoLink(),
                        todo.getTodoPic(),
                        todo.getCreatedAt()
                ))
                .toList();

        return new SliceImpl<>(responseList, pageable, todos.hasNext());
    }

    @Override
    public CreateTodoResponse saveTodo(int userId, CreateTodoRequest request) {
        String uploadUrl = "";
        if (Objects.nonNull(request.imageEncodedBase64()) && !request.imageEncodedBase64().isEmpty()) {
            uploadUrl = storageService.uploadFile(request.imageEncodedBase64(), request.imageName());
        }

        Goal goal = goalRepository.findById(request.goalId())
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(request.goalId()), "goal"));
        Todo todo = request.toEntity(uploadUrl, goal);
        Todo savedTodo = todoRepository.save(todo);

        return CreateTodoResponse.fromEntity(savedTodo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReadTodosWithGoalsResponse> findTodoListWithGoals(int userId, @Valid ReadTodoWithGoalRequest request) {
        List<Goal> goals = goalRepository.findByUser_UserId(userId);

        return goals.stream()
                .map(goal -> {
                    Pageable pageable = PageRequest.of(0, request.size());
                    Slice<Todo> todos = todoRepository.findByGoal_GoalIdOrderByTodoIdDesc(goal.getGoalId(), pageable);

                    List<ReadTodosResponse> responses = todos.getContent().stream()
                            .map(todo -> new ReadTodosResponse(
                                    todo.getTodoId(),
                                    todo.getTodoTitle(),
                                    todo.getStartDate(),
                                    todo.getEndDate(),
                                    todo.getTodoStatus(),
                                    todo.getTodoLink(),
                                    todo.getTodoPic(),
                                    todo.getCreatedAt()
                            ))
                            .toList();

                    return new ReadTodosWithGoalsResponse(
                            goal.getGoalId(),
                            goal.getGoalTitle(),
                            responses
                    );

                })
                .toList();
    }
}
