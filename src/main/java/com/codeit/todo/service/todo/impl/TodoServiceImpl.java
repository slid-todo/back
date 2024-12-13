package com.codeit.todo.service.todo.impl;

import com.codeit.todo.common.exception.goal.GoalNotFoundException;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.TodoRepository;
import com.codeit.todo.service.storage.StorageService;
import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.web.dto.request.todo.CreateTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoWithGoalRequest;
import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import com.codeit.todo.web.dto.response.todo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final GoalRepository goalRepository;
    private final CompleteRepository completeRepository;

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
                .map(todo -> {
                    List<Complete> completes = completeRepository.findByTodo_TodoId(todo.getTodoId());

                    List<ReadCompleteResponse> completeResponses = completes.stream().map(
                            complete -> ReadCompleteResponse.builder()
                                    .completeId(complete.getCompleteId())
                                    .note(complete.getNote())
                                    .completeFile(complete.getCompleteFile())
                                    .completeLink(complete.getCompleteLink())
                                    .completePic(complete.getCompletePic())
                                    .createdAt(complete.getCreatedAt())
                                    .completedDate(complete.getCompletedDate())
                                    .build()
                    ).toList();

                    return ReadTodosResponse.builder()
                            .todoId(todo.getTodoId())
                            .todoLink(todo.getTodoLink())
                            .todoStatus(todo.getTodoStatus())
                            .todoTitle(todo.getTodoTitle())
                            .startDate(todo.getStartDate())
                            .endDate(todo.getEndDate())
                            .todoPic(todo.getTodoPic())
                            .createdAt(todo.getCreatedAt())
                            .completes(completeResponses)
                            .build();
                }).toList();

        return new SliceImpl<>(responseList, pageable, todos.hasNext());
    }

    @Override
    public CreateTodoResponse saveTodo(int userId, CreateTodoRequest request) {
        String uploadUrl = "";
        if (Objects.nonNull(request.imageEncodedBase64()) && !request.imageEncodedBase64().isEmpty()) {
            uploadUrl = storageService.uploadFile(request.imageEncodedBase64(), request.imageName());
        }

        Goal goal = goalRepository.findByGoalIdAndUser_UserId(request.goalId(), userId)
                .orElseThrow(() -> new GoalNotFoundException(String.valueOf(request.goalId())));
        Todo todo = request.toEntity(uploadUrl, goal);
        Todo savedTodo = todoRepository.save(todo);

        List<Complete> completes = new ArrayList<>();
        long completeDate = ChronoUnit.DAYS.between(request.startDate(), request.endDate());

        for (long i = 0; i <= completeDate; i++) {
            LocalDate date = request.startDate().plusDays(i);

            Complete complete = Complete.builder()
                    .todo(savedTodo)
                    .completedDate(date)
                    .createdAt(LocalDateTime.now())
                    .completeStatus(false)
                    .build();

            completes.add(complete);
        }

        completeRepository.saveAll(completes);

        return CreateTodoResponse.fromEntity(savedTodo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReadTodosWithGoalsResponse> findTodoListWithGoals(int userId, ReadTodoWithGoalRequest request) {
        List<Goal> goals = goalRepository.findByUser_UserId(userId);

        return goals.stream()
                .map(goal -> {
                    Pageable pageable = PageRequest.of(0, request.size());
                    Slice<Todo> todos = todoRepository.findByGoal_GoalIdOrderByTodoIdDesc(goal.getGoalId(), pageable);

                    List<ReadTodosResponse> responses = todos.getContent().stream()
                            .map(todo -> {
                                List<Complete> completes = completeRepository.findByTodo_TodoId(todo.getTodoId());

                                List<ReadCompleteResponse> completeResponses = completes.stream().map(
                                        complete -> ReadCompleteResponse.builder()
                                                .completeId(complete.getCompleteId())
                                                .note(complete.getNote())
                                                .completeFile(complete.getCompleteFile())
                                                .completeLink(complete.getCompleteLink())
                                                .completePic(complete.getCompletePic())
                                                .createdAt(complete.getCreatedAt())
                                                .completedDate(complete.getCompletedDate())
                                                .build()
                                ).toList();

                                return ReadTodosResponse.builder()
                                        .todoId(todo.getTodoId())
                                        .todoLink(todo.getTodoLink())
                                        .todoStatus(todo.getTodoStatus())
                                        .todoTitle(todo.getTodoTitle())
                                        .startDate(todo.getStartDate())
                                        .endDate(todo.getEndDate())
                                        .todoPic(todo.getTodoPic())
                                        .createdAt(todo.getCreatedAt())
                                        .completes(completeResponses)
                                        .build();
                                    }).toList();

                    return new ReadTodosWithGoalsResponse(
                            goal.getGoalId(),
                            goal.getGoalTitle(),
                            responses
                    );

                }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Slice<ReadTodoWithGoalResponse> findTodoListWithGoal(int userId, int goalId, ReadTodoWithGoalRequest request) {
        Slice<Todo> todos;
        Pageable pageable = PageRequest.of(0, request.size());

        if (Objects.isNull(request.lastTodoId()) || request.lastTodoId() <= 0) {
            todos = todoRepository.findByGoal_GoalIdOrderByTodoIdDesc(goalId, pageable);
        } else {
            todos = todoRepository.findByGoal_GoalIdAndTodoIdLessThanOrderByTodoIdDesc(goalId, request.lastTodoId(), pageable);
        }

        List<ReadTodoWithGoalResponse> responses = todos.stream().map(
                todo -> new ReadTodoWithGoalResponse(
                        todo.getTodoId(),
                        todo.getTodoTitle(),
                        todo.getStartDate(),
                        todo.getEndDate(),
                        todo.getTodoStatus(),
                        todo.getTodoLink(),
                        todo.getTodoPic(),
                        todo.getCreatedAt(),
                        todo.getGoal().getGoalId()
                )
        ).toList();

        return new SliceImpl<>(responses, pageable, todos.hasNext());
    }

    @Transactional(readOnly = true)
    @Override
    public ReadTodoProgressResponse calculateTodoProgress(int userId) {
        LocalDate today = LocalDate.now();
        List<Goal> goals = goalRepository.findByUser_UserId(userId);
        List<Integer> goalIds = goals.stream()
                .map(Goal::getGoalId)
                .toList();

        List<Todo> todos = todoRepository.findByGoal_GoalIdInAndStartDate(goalIds, today);

        int totalTodos = todos.size();
        long completedTodo = todos.stream()
                .filter(todo -> todo.getTodoStatus().equals("인증"))
                .count();

        double progress = totalTodos > 0 ? (double) completedTodo / totalTodos * 100 : 0;

        return ReadTodoProgressResponse.from(progress);
    }
}
