package com.codeit.todo.repository;

import com.codeit.todo.domain.Complete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteRepository extends JpaRepository<Complete, Integer> {
    List<Complete> findByTodo_TodoId(int todoId);
}
