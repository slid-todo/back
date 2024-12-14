package com.codeit.todo.repository;

import com.codeit.todo.domain.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Slice<Todo> findByGoal_GoalIdInAndTodoIdLessThanOrderByTodoIdDesc(List<Integer> goalIds, Integer lastTodoId, Pageable pageable);

    Slice<Todo> findByGoal_GoalIdInOrderByTodoIdDesc(List<Integer> goalIds, Pageable pageable);

    Slice<Todo> findByGoal_GoalIdOrderByTodoIdDesc(int goalId, Pageable pageable);

    Slice<Todo> findByGoal_GoalIdAndTodoIdLessThanOrderByTodoIdDesc(int goalId, Integer lastTodoId, Pageable pageable);

    List<Todo> findByGoal_GoalIdInAndStartDate(List<Integer> goalIds, LocalDate today);
}
