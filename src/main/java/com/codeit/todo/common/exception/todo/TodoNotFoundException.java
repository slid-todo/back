package com.codeit.todo.common.exception.todo;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class TodoNotFoundException extends EntityNotFoundException {

    /**
     * @param request    엔티티를 찾기 위해 요청한 값
     */
    public TodoNotFoundException(String request) {
        super(request, "todo");
    }
}
