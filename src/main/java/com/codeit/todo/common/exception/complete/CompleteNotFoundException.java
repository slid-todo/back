package com.codeit.todo.common.exception.complete;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class CompleteNotFoundException extends EntityNotFoundException {
    private static final String ENTITY_TYPE = "complete";

    /**
     * @param request    엔티티를 찾기 위해 요청한 값
     */
    public CompleteNotFoundException(String request) {
        super(request, ENTITY_TYPE);
    }
}
