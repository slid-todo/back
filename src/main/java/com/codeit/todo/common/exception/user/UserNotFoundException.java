package com.codeit.todo.common.exception.user;


import com.codeit.todo.common.exception.EntityNotFoundException;
import java.time.LocalDateTime;

/**
 * User 엔티티를 찾지 못했을 경우 발생하는 예외입니다.
 *
 * @see EntityNotFoundException
 */

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String entityId, String entityType) {
        super(entityId, entityType);
    }
}
