package com.codeit.todo.repository;

import com.codeit.todo.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Boolean existsByUser_UserIdAndComplete_CompleteId(int userId, int completeId);
}
