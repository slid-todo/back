package com.codeit.todo.service.todo.impl;

import com.codeit.todo.common.exception.auth.AuthorizationDeniedException;
import com.codeit.todo.common.exception.goal.GoalNotFoundException;
import com.codeit.todo.common.exception.todo.TodoNotFoundException;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.Todo;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.TodoRepository;
import com.codeit.todo.service.storage.StorageService;
import com.codeit.todo.service.todo.TodoService;
import com.codeit.todo.web.dto.request.todo.*;
import com.codeit.todo.web.dto.response.complete.ReadCompleteResponse;
import com.codeit.todo.web.dto.response.slice.CustomSlice;
import com.codeit.todo.web.dto.response.todo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private static final String COMPLETE = "인증";

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

        List<ReadTodosResponse> responses = getTodoResponses(todos);

        return new SliceImpl<>(responses, pageable, todos.hasNext());
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
                    .startDate(date)
                    .createdAt(LocalDateTime.now())
                    .completeStatus("진행")
                    .build();

            completes.add(complete);
        }

        completeRepository.saveAll(completes);

        return CreateTodoResponse.fromEntity(savedTodo);
    }

    @Transactional(readOnly = true)
    @Override
    public Slice<ReadTodosWithGoalsResponse> findTodoListWithGoals(int userId, ReadTodoCompleteWithGoalRequest request) {
        int pageSize = request.size();
        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<Goal> goals;
        LocalDate today = LocalDate.now();

        if (Objects.isNull(request.lastGoalId()) || request.lastGoalId() <= 0) {
            goals = goalRepository.findByUserAndHasTodos(userId, pageable, today);
        } else {
            goals = goalRepository.findByUserAndHasTodosAfterLastGoalId(request.lastGoalId(), userId, pageable, today);
        }

        List<ReadTodosWithGoalsResponse> responses = goals.getContent().stream()
                .map(goal -> {
                    List<Todo> todos = goal.getTodos();
                    List<ReadTodosResponse> todosResponses = makeTodosResponses(todos);

                    double goalProgress = calculateGoalProgress(todos);

                    return ReadTodosWithGoalsResponse.from(goal, todosResponses, goalProgress);
                })
                .toList();

        Integer nextCursor = goals.hasNext()
                ? goals.getContent().get(goals.getContent().size() - 1).getGoalId()
                : null;

        return new CustomSlice<>(responses, pageable, goals.hasNext(), nextCursor);
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
                ReadTodoWithGoalResponse::from
        ).toList();

        return new SliceImpl<>(responses, pageable, todos.hasNext());
    }

    @Transactional(readOnly = true)
    @Override
    public ReadTodoProgressResponse calculateTodoProgress(int userId) {
        LocalDate today = LocalDate.now();
        List<Todo> todos = getTodayTodos(today, userId);

        long totalCompletes = 0;
        long certifiedCompletes = 0;

        for (Todo todo : todos) {
            totalCompletes += todo.getCompletes().stream()
                    .filter(complete -> today.equals(complete.getStartDate()))
                    .count();

            certifiedCompletes += todo.getCompletes().stream()
                    .filter(complete -> COMPLETE.equals(complete.getCompleteStatus()))
                    .count();
        }

        double completeProgress = totalCompletes > 0 ? (double) certifiedCompletes / totalCompletes * 100 : 0;

        log.info("할 일 진행상황 조회 성공 : {}", completeProgress);
        return ReadTodoProgressResponse.from(completeProgress);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReadTodayTodoResponse> findTodayTodo(int userId) {
        LocalDate today = LocalDate.now();
        List<Todo> todos = getTodayTodos(today, userId);

        return todos.stream()
                .map(todo -> {
                    ReadCompleteResponse todayComplete = todo.getCompletes().stream()
                            .filter(complete -> today.equals(complete.getStartDate()))
                            .map(ReadCompleteResponse::from)
                            .findFirst()
                            .orElse(null);

                    return ReadTodayTodoResponse.from(todo, todayComplete);

                }).toList();
    }

    @Override
    public UpdateTodoResponse updateTodo(UpdateTodoRequest request, int userId, int todoId) {
        Todo todo = getTodo(userId, todoId);

        String uploadPicUrl = "";
        if (Objects.nonNull(request.imageEncodedBase64()) && !request.imageEncodedBase64().isEmpty()) {
            uploadPicUrl = storageService.uploadFile(request.imageEncodedBase64(), request.imageName());
        }

        todo.update(request, uploadPicUrl);

        log.info("할 일 수정 성공. 수정된 할일 ID : {}", todoId);

        return UpdateTodoResponse.fromEntity(todo);
    }

    @Override
    public DeleteTodoResponse deleteTodo(int userId, int todoId) {
        Todo todo = getTodo(userId, todoId);
        todoRepository.delete(todo);

        return DeleteTodoResponse.from(todoId);
    }

    @Override
    public ReadTodoDetailResponse getTodoDetail(int userId, int todoId) {
        Todo todo = getTodo(userId, todoId);
        return ReadTodoDetailResponse.from(todo);
    }

    private Todo getTodo(int userId, int todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException(String.valueOf(todoId)));

        if (todo.getGoal().getUser().getUserId() != userId) {
            log.error("할일 작업 거부됨. 요청 유저 ID : {}", userId);
            throw new AuthorizationDeniedException("할 일 작업에 대한 권한이 없습니다.");
        }

        return todo;
    }

    private List<Todo> getTodayTodos(LocalDate today, int userId) {
        List<Goal> goals = goalRepository.findByUser_UserId(userId);
        List<Integer> goalIds = goals.stream()
                .map(Goal::getGoalId)
                .toList();

        return todoRepository.findTodosByGoalIdsBetweenDates(goalIds, today);
    }

    private List<ReadTodosResponse> getTodoResponses(Slice<Todo> todos) {
        return todos.getContent().stream()
                .map(todo -> {
                    List<Complete> completes = completeRepository.findByTodo_TodoId(todo.getTodoId());

                    List<ReadCompleteResponse> completeResponses = completes.stream().map(
                                    ReadCompleteResponse::from)
                            .toList();

                    return ReadTodosResponse.from(todo, completeResponses);
                }).toList();
    }

    public double calculateGoalProgress(List<Todo> todos) {
        long totalCompletes = 0;
        long completedCompletes = 0;

        for (Todo todo : todos) {
            List<Complete> completes = completeRepository.findByTodo_TodoId(todo.getTodoId());
            totalCompletes += completes.size();
            completedCompletes += completes.stream()
                    .filter(complete -> COMPLETE.equals(complete.getCompleteStatus()))
                    .count();
        }

        return totalCompletes > 0 ? (completedCompletes / (double) totalCompletes) * 100 : 0;
    }

    public List<ReadTodosResponse> makeTodosResponses(List<Todo> todos){
        return todos.stream()
                .map(todo -> {
                    List<Complete> completes = completeRepository.findByTodo_TodoId(todo.getTodoId());

                    List<ReadCompleteResponse> completeResponses = completes.stream()
                            .map(ReadCompleteResponse::from)
                            .toList();

                    return ReadTodosResponse.from(todo, completeResponses);
                }).toList();
    }

    public Slice<Goal> getGoalsPagination(int userId, ReadTodoCompleteWithGoalRequest request, Pageable pageable){
        Slice<Goal> goals;
        if (Objects.isNull(request.lastGoalId()) || request.lastGoalId() <= 0) {
            goals = goalRepository.findByUser_UserId(userId, pageable);
        } else {
            goals = goalRepository.findByGoalIdAndUser_UserId(request.lastGoalId(), userId, pageable);
        }

        return goals;
    }
}
