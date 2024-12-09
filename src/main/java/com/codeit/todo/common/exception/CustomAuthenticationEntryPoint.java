package com.codeit.todo.common.exception;

import com.codeit.todo.common.exception.payload.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;


@Getter
public class CustomAuthenticationEntryPoint extends ApplicationException {

    private static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE = "권한이 없습니다.";
    private static final int ENTITY_NOT_FOUND_EXCEPTION_STATUS_CODE = 401;

    private final String entityId;
    private final String entityType;

    public CustomAuthenticationEntryPoint(String entityId, String entityType) {
        super(new ErrorStatus(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE, ENTITY_NOT_FOUND_EXCEPTION_STATUS_CODE, LocalDateTime.now()));
        this.entityId = entityId;
        this.entityType = entityType;
    }

}
