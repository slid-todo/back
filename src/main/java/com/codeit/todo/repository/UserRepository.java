package com.codeit.todo.repository;


import com.codeit.todo.domain.Todo;
import com.codeit.todo.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);

    List<User> findByNameContains(@Param("keyword") String keyword);
}
