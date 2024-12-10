package com.codeit.todo.repository;

import com.codeit.todo.domain.Complete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteRepository extends JpaRepository<Complete, Integer> {
}
