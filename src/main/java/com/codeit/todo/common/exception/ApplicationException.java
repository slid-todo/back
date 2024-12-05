package com.codeit.todo.common.exception;

import com.codeit.todo.common.exception.payload.ErrorStatus;
import lombok.Getter;

/**
 * 어플리케이션에서 발생하는 예외를 나타내는 클래스입니다. 앞으로 작성할 예외는 해당 예외 클래스를 상속하는 형태로 구현하시면 됩니다. (EntityNotFoundException 참고)
 * 에러 코드, 메세지, 발생시간을 저장합니다.
 */
@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorStatus errorStatus;

    /**
     *
     * @param errorStatus 상태 코드, 메세지, 발생시간을 저장한 객체
     */
    public ApplicationException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
}
