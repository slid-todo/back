package com.codeit.todo.common.exception.user;

import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;

public class UpdatePasswordException extends ApplicationException {
    /**
     * @param errorStatus 상태 코드, 메세지, 발생시간을 저장한 객체
     */
    public UpdatePasswordException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
