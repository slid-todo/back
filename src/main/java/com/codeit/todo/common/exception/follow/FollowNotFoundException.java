package com.codeit.todo.common.exception.follow;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class FollowNotFoundException extends EntityNotFoundException {
    private static final String ENTITY_TYPE = "follow";

    /**
     * @param request    엔티티를 찾기 위해 요청한 값
     */
    public FollowNotFoundException(String request) {
        super(request, ENTITY_TYPE);
    }
}
