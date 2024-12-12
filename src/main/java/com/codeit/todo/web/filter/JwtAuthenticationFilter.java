package com.codeit.todo.web.filter;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String jwtToken = jwtTokenProvider.resolveToken(request);


        if (jwtToken == null) {
            String requestURI = request.getRequestURI();
            if(requestURI.equals("/api/v1/auths/login")){
                filterChain.doFilter(request, response);
            }
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("no token", 400)
            );
        }

//        try {
//            String jwtToken = jwtTokenProvider.resolveToken(request);
//
//            if (jwtToken != null && jwtTokenProvider.validToken(jwtToken)) {
//                Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//            }
//        }catch(Exception e){
//            logger.error("JWT authentication failed");
//            throw new ApplicationException(
//                    ErrorStatus.toErrorStatus("실패" + e.getMessage(), 500)
//            );
//        }

        if (jwtToken != null && jwtTokenProvider.validToken(jwtToken)) {
            Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
