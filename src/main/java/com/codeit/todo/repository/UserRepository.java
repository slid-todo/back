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

    //@Query(value = "SELECT * FROM user WHERE MATCH(name) AGAINST(:keyword IN BOOLEAN MODE) LIMIT 10000", nativeQuery = true)
    //List<User> searchByFullTextName(@Param("keyword") String keyword);

    List<User> findByNameStartingWith(@Param("keyword") String keyword);

    //List<User> findByNameContains(@Param("keyword") String keyword);
}
