package com.codeit.todo.common.exception.goal;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class GoalNotFoundException extends EntityNotFoundException {

    public GoalNotFoundException(String entityId, String entityType) {
        super(entityId, entityType);
    }
}
