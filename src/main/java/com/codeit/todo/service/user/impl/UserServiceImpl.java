package com.codeit.todo.service.user.impl;

import com.codeit.todo.common.config.JwtTokenProvider;
import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.codeit.todo.common.exception.user.SignUpException;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import com.codeit.todo.domain.User;
import com.codeit.todo.repository.UserRepository;
import com.codeit.todo.service.user.UserService;
import com.codeit.todo.web.dto.request.auth.LoginRequest;
import com.codeit.todo.web.dto.request.auth.SignUpRequest;
import com.codeit.todo.web.dto.response.auth.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private static final int CONFLICT = 409;

    private static final int BAD_REQUEST = 400;

    @Override
    public SignUpResponse signUpUser(SignUpRequest request) {
        //기존에 있는 이메일인지 확인
        String email = request.email();
        if(userRepository.findByEmail(email).isPresent()) throw new SignUpException(ErrorStatus.toErrorStatus("이미 존재하는 이메일입니다", CONFLICT));

        //비밀번호, 비밀번호 확인이 일치하는지 확인
        String password = request.password();
        String passwordCheck = request.passwordCheck();
        if(!password.equals(passwordCheck)) throw new SignUpException(ErrorStatus.toErrorStatus("비밀번호가 일치하지 않습니다", BAD_REQUEST));

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = request.toEntity(encodedPassword);
        User savedUser = userRepository.save(user);

        return new SignUpResponse(savedUser.getUserId());
    }


    public String login(LoginRequest loginRequest){
        String email = loginRequest.email();
        String password = loginRequest.password();

        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new UserNotFoundException(email, "User"));

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

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
