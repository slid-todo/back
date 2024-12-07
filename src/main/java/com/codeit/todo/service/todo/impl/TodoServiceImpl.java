package com.codeit.todo.service.todo.impl;

import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.TodoRepository;
import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.response.todo.ReadTodosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
}
