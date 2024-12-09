package com.codeit.todo.service.todo;

import com.codeit.todo.web.dto.request.todo.CreateTodoRequest;
import com.codeit.todo.web.dto.request.todo.ReadTodoRequest;
import com.codeit.todo.web.dto.response.todo.CreateTodoResponse;
import com.codeit.todo.web.dto.response.todo.ReadTodosResponse;
import org.springframework.data.domain.Slice;

public interface TodoService {

    Slice<ReadTodosResponse> findTodoList(int userId, ReadTodoRequest request);

    CreateTodoResponse saveTodo(int userId, CreateTodoRequest request);
}
