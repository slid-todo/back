package com.codeit.todo.common.exception.goal;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class GoalNotFoundException extends EntityNotFoundException {

    private static final String ENTITY_TYPE = "goal";

    public GoalNotFoundException(String request) {
        super(request, ENTITY_TYPE);
    }
}
