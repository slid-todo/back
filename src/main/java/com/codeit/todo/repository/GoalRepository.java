package com.codeit.todo.repository;

import com.codeit.todo.domain.Goal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findByUser_UserId(int userId);

    Optional<Goal> findByGoalIdAndUser_UserId(int goalId, int userId);

    Slice<Goal> findByUser_UserId(@Param("userId") int userId, Pageable pageable);

    @Query(
            "SELECT g FROM Goal g " +
                    "WHERE g.user.userId = :userId " +
                    "AND g.goalId > :lastGoalId "
    )
    Slice<Goal> findByGoalIdAndUser_UserIdAndGoalIdGreaterThan(@Param("userId") int userId, @Param("lastGoalId")int lastGoalId, Pageable pageable);

    @Query("""
select g
from Goal g
join fetch g.todos t
where g.user.userId = :userId
and :today between t.startDate and t.endDate
""")
    Slice<Goal> findByUserAndHasTodos(@Param("userId") int userId, Pageable pageable, @Param("today") LocalDate today);

    @Query("""
select g
from Goal g
join fetch g.todos t
where g.user.userId = :userId
and g.goalId > :lastGoalId
and :today between t.startDate and t.endDate
""")
    Slice<Goal> findByUserAndHasTodosAfterLastGoalId(@Param("lastGoalId") Integer lastGoalId, @Param("userId") int userId, Pageable pageable,  @Param("today") LocalDate today);

    List<Goal> findByGoalTitleContains(@Param("keyword") String keyword);

}
