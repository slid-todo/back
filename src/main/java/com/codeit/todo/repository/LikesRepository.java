package com.codeit.todo.repository;

import com.codeit.todo.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Boolean existsByUser_UserIdAndComplete_CompleteId(int userId, int completeId);

    Optional<Likes> findByComplete_CompleteIdAndUser_UserId(int completeId, int userId);

    List<Likes> findByUser_UserId(int userId);
}
