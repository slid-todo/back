package com.codeit.todo.service.user.impl;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import com.codeit.todo.domain.User;
import com.codeit.todo.repository.UserRepository;
import com.codeit.todo.service.user.UserService;
import com.codeit.todo.web.dto.request.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;



    public String login(LoginRequest loginRequest){
        String email = loginRequest.email();
        String password = loginRequest.password();

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new UserNotFoundException(email, "User"));

            return jwtTokenProvider.createToken(email);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException(new ErrorStatus(
                    "로그인 과정에서 에러 발생",
                    500,
                    LocalDateTime.now()
            ));
        }
    }

}
