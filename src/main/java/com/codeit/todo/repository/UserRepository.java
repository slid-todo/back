package com.codeit.todo.repository;


import com.codeit.todo.domain.Todo;
import com.codeit.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);
}
