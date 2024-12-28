package com.codeit.todo.common.exception.comment;

import com.codeit.todo.common.exception.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
    private static final String ENTITY_TYPE = "comment";

    /**
     * @param request    엔티티를 찾기 위해 요청한 값
     */
    public CommentNotFoundException(String request) {
        super(request, ENTITY_TYPE);
    }
}
