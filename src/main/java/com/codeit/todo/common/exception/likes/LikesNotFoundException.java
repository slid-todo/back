package com.codeit.todo.common.exception.likes;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class LikesNotFoundException extends EntityNotFoundException {
    private static final String ENTITY_TYPE = "likes";

    /**
     * @param request    엔티티를 찾기 위해 요청한 값
     */
    public LikesNotFoundException(String request) {
        super(request, ENTITY_TYPE);
    }
}