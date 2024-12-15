package com.codeit.todo.service.todo;

import com.codeit.todo.web.dto.request.todo.CreateTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoWithGoalRequest;
import com.codeit.todo.web.dto.request.todo.UpdateTodoRequest;
import com.codeit.todo.web.dto.response.todo.*;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TodoService {

    Slice<ReadTodosResponse> findTodoList(int userId, ReadTodoRequest request);

    CreateTodoResponse saveTodo(int userId, CreateTodoRequest request);

    List<ReadTodosWithGoalsResponse> findTodoListWithGoals(int userId, ReadTodoWithGoalRequest request);

    Slice<ReadTodoWithGoalResponse> findTodoListWithGoal(int userId, int goalId, ReadTodoWithGoalRequest request);

    ReadTodoProgressResponse calculateTodoProgress(int userId);

    List<ReadTodayTodoResponse> findTodayTodo(int userId);

    UpdateTodoResponse updateTodo(UpdateTodoRequest request, int userId, int todoId);

    DeleteTodoResponse deleteTodo(int userId, int todoId);
}
