package com.codeit.todo.repository;

import com.codeit.todo.domain.Complete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompleteRepository extends JpaRepository<Complete, Integer> {
    List<Complete> findByTodo_TodoId(int todoId);

    @Query("select c from Complete c where c.todo.goal.user.userId in :userIds order by c.createdAt desc")
    Slice<Complete> findByFollowees(@Param("userIds") List<Integer> userIds, Pageable pageable);

    @Query("select c from Complete c where c.todo.goal.user.userId in :userIds and c.completeId < :completeId order by c.createdAt desc")
    Slice<Complete> findByFolloweesAfterCompleteId(@Param("userIds")List<Integer> followeeIds, @Param("completeId") Integer completeId, Pageable pageable);
}
