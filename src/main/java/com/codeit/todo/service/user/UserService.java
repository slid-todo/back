package com.codeit.todo.service.user;

import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.request.auth.SignUpRequest;
import com.codeit.todo.web.dto.request.auth.UpdatePictureRequest;
import com.codeit.todo.web.dto.response.auth.ReadUserResponse;
import com.codeit.todo.web.dto.response.auth.SignUpResponse;
import com.codeit.todo.web.dto.response.auth.UpdatePictureResponse;

public interface UserService {

    SignUpResponse signUpUser(SignUpRequest signUpRequest);

    String login(LoginRequest loginRequest);

    ReadUserResponse findUserInfo(int userId);

    UpdatePictureResponse updateProfilePicture(int userId, UpdatePictureRequest pictureRequest);
}
