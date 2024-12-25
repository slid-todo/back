package com.codeit.todo.service.todo;

import com.codeit.todo.web.dto.request.todo.*;
import com.codeit.todo.web.dto.response.todo.*;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TodoService {

    Slice<ReadTodosResponse> findTodoList(int userId, ReadTodoRequest request);

    CreateTodoResponse saveTodo(int userId, CreateTodoRequest request);

    Slice<ReadTodosWithGoalsResponse> findTodoListWithGoals(int userId, ReadTodoCompleteWithGoalRequest request);

    Slice<ReadTodoWithGoalResponse> findTodoListWithGoal(int userId, int goalId, ReadTodoWithGoalRequest request);

    ReadTodoProgressResponse calculateTodoProgress(int userId);

    List<ReadTodayTodoResponse> findTodayTodo(int userId);

    UpdateTodoResponse updateTodo(UpdateTodoRequest request, int userId, int todoId);

    DeleteTodoResponse deleteTodo(int userId, int todoId);

    ReadTodoDetailResponse getTodoDetail(int userId, int todoId);
}
