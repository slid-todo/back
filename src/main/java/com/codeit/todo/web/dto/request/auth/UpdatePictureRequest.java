package com.codeit.todo.web.dto.request.auth;

public record UpdatePictureRequest (
        String profilePicBase64,
        String profilePicName
){
}
