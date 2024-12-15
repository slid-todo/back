package com.codeit.todo.common.exception.auth;

import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;


public class AuthorizationDeniedException extends ApplicationException {

    public AuthorizationDeniedException(String message) {
        super(ErrorStatus.toErrorStatus(
                message, 403));
    }
}
