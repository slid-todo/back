package com.codeit.todo.repository;


import com.codeit.todo.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

