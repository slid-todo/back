package com.codeit.todo.repository;

import com.codeit.todo.domain.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Slice<Todo> findByGoal_GoalIdInAndTodoIdLessThanOrderByTodoIdDesc(List<Integer> goalIds, Integer lastTodoId, Pageable pageable);

    Slice<Todo> findByGoal_GoalIdInOrderByTodoIdDesc(List<Integer> goalIds, Pageable pageable);

    Slice<Todo> findByGoal_GoalIdOrderByTodoIdDesc(int goalId, Pageable pageable);

    Slice<Todo> findByGoal_GoalIdAndTodoIdLessThanOrderByTodoIdDesc(int goalId, Integer lastTodoId, Pageable pageable);

    @Query("select t from Todo t where t.goal.goalId in :goalIds and :today between t.startDate and t.endDate")
    List<Todo> findTodosByGoalIdsBetweenDates(@Param("goalIds") List<Integer> goalIds, @Param("today") LocalDate today);

    @Query("select t from Todo t where t.goal.goalId = :goalId and :today between t.startDate and t.endDate")
    List<Todo> findTodosByGoalIdBetweenDates(@Param("goalId") int goalId, @Param("today") LocalDate today);

    List<Todo> findByGoal_GoalId(int goalId);
}
