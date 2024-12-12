package com.codeit.todo.common.exception.jwt;

import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;

public class JwtException extends ApplicationException {
    /**
     * @param errorStatus 상태 코드, 메세지, 발생시간을 저장한 객체
     */
    public JwtException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
