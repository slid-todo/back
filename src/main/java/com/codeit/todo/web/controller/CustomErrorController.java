//package com.codeit.todo.web.controller;
//
//import com.codeit.todo.common.exception.JwtNotFoundException;
//import com.codeit.todo.common.exception.payload.ErrorStatus;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class CustomErrorController implements ErrorController {
//
//    @RequestMapping("/error")
//    public ResponseEntity<ErrorStatus> handleError(HttpServletRequest request) {
//        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//
//        if (throwable instanceof JwtNotFoundException exception) {
//            ErrorStatus errorStatus = exception.getErrorStatus();
//            return new ResponseEntity<>(errorStatus, errorStatus.toHttpStatus());
//        }
//
//        // Fallback for non-ApplicationException errors
//        return new ResponseEntity<>(
//                ErrorStatus.toErrorStatus("JWT 없음", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//
//
//
//}
