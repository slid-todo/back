package com.codeit.todo.service.user;

import com.codeit.todo.web.dto.request.auth.LoginRequest;

public interface UserService {

    String login(LoginRequest loginRequest);
}
