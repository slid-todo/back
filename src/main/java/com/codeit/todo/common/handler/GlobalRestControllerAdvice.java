package com.codeit.todo.common.handler;

import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(value="/api/v1/exceptions")
public class GlobalRestControllerAdvice {

    /**
     * ApplicationException 예외를 상속받는 모든 예외를 처리하는 메소드입니다.
     * 예외 처리 시, HTTP 상태 코드와 오류 정보를 포함한 응답을 반환합니다.
     *
     * @param e 발생한 예외
     * @return 해당 HTTP 상태 코드와 오류 정보를 반환
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorStatus> handleApplicationException(ApplicationException e) {
        ErrorStatus errorStatus = e.getErrorStatus();

        return new ResponseEntity<>(errorStatus, errorStatus.toHttpStatus());
    }

    @GetMapping(value= "/entrypoint")
    public void entryPointException(){
        throw new NullPointerException("로그인이 필요합니다");
    }

    @GetMapping(value="/access-denied")
    public void accessDeniedException(){
        throw new NullPointerException("권한이 없습니다.");
    }
}
