package com.codeit.todo.web.filter;

import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch(Exception e){
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("JWT를 찾을 수 없습니다" + e.getMessage(), 401)
            );
        }
    }



}
