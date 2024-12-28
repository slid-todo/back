package com.codeit.todo.service.likes;

import com.codeit.todo.web.dto.response.likes.CreateLikeResponse;
import com.codeit.todo.web.dto.response.likes.DeleteLikeResponse;

public interface LikesService {
    CreateLikeResponse createLikes(int userId, int completeId);

    DeleteLikeResponse deleteLikes(int userId, int completeId);
}
