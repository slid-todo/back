package com.codeit.todo.repository;


import com.codeit.todo.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByUser_UserId(int userId);
}

