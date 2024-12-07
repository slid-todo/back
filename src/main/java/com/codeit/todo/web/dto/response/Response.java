package com.codeit.todo.web.dto.response;

import java.time.LocalDateTime;

/**
 *
 * @param statusCode 응답코드
 * @param data       응답 데이터
 * @param timestamp  응답 시간
 */
public record Response<T>(
        int statusCode,
        T data,
        LocalDateTime timestamp
) {

    public Response(int statusCode, T data) {
        this(statusCode, data, LocalDateTime.now());
    }

    // 응답 시, 해당 메소드를 활용합니다.
    public static <T> Response<T> ok(T data) {
        return new Response<>(200, data);
    }
}
