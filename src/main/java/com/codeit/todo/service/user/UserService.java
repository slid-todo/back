package com.codeit.todo.service.user;

import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.request.auth.SignUpRequest;
import com.codeit.todo.web.dto.response.auth.SignUpResponse;

public interface UserService {

    SignUpResponse signUpUser(SignUpRequest signUpRequest);

    String login(LoginRequest loginRequest);

}
