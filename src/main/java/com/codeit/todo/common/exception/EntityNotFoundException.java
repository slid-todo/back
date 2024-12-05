package com.codeit.todo.common.exception;

import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 엔티티를 찾지 못했을 경우 발생하는 상위 예외 클래스입니다. 구체적인 예외 클래스는 해당 클래스를 상속받아 구현합니다. (UserNotFoundException 참고)
 *
 * @see UserNotFoundException
 * @see ApplicationException
 */
@Getter
public class EntityNotFoundException extends ApplicationException{
    private final String entityId;
    private final String entityType;

    /**
     *
     * @param entityId 엔티티 ID
     * @param entityType 엔티티 타입 (User 등)
     */
    public EntityNotFoundException(String entityId, String entityType) {
        super(new ErrorStatus("해당하는 엔티티를 찾을 수 없습니다.", 404, LocalDateTime.now()));
        this.entityId = entityId;
        this.entityType = entityType;
    }
}
