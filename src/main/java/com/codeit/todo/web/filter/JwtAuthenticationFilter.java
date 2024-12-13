package com.codeit.todo.web.filter;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.jwt.JwtException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = jwtTokenProvider.resolveToken(request);

        try {
            if (jwtToken == null) {
                throw new JwtException(ErrorStatus.toErrorStatus(
                        "쿠키의 JWT 토큰이 비어있습니다.", 401
                ));
            }

            log.info("토큰이 인식되었습니다. : {}", jwtToken);
            if (jwtTokenProvider.validToken(jwtToken)) {
                log.info("토큰이 유효합니다. : {}", jwtToken);
                Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (ApplicationException e) {
            log.info("토큰 에러 발생 : {}", jwtToken);
            processExceptionHandle(response, e.getErrorStatus());
            return;
            }

        log.info("필터를 통과합니다");
        filterChain.doFilter(request, response);
    }

    /**
     * 필터 내부에서 예외처리하는 메소드
     * @param response HttpServletResponse
     * @param errorStatus catch에서 받은 ApplicationException의 ErrorStatus
     */
    private void processExceptionHandle(HttpServletResponse response, ErrorStatus errorStatus) {
        response.setStatus(errorStatus.status());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String json = new ObjectMapper().writeValueAsString(errorStatus);
            response.getWriter().write(json);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 필터링을 하지 않도록 하는 메소드입니다. excludedPaths 배열 안의 엔드포인트는 필터를 하지 않게 됩니다.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludedPaths = {"/api/v1/auths/signup", "/api/v1/auths/login", "/v3/**", "/swagger-ui/**"};
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        for (String excludedPath : excludedPaths) {
            if (antPathMatcher.match(excludedPath, request.getRequestURI())) {
                return true;
            }
        }

        return false;
    }
}
