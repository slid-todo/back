package com.codeit.todo.web.dto.response.auth;

import com.codeit.todo.domain.Goal;
import com.codeit.todo.web.dto.response.goal.CreateGoalResponse;
import lombok.Builder;

@Builder
public record SignUpResponse(int userId ) { }
