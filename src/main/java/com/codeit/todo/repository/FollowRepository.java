package com.codeit.todo.repository;


import com.codeit.todo.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Query(
            "SELECT COUNT(f) > 0 " +
                    "FROM Follow f " +
                    "WHERE f.follower.userId = :userId " +
                    "AND f.followee.userId = :targetUserId "
    )
    boolean existsByFollower_FollowerIdAndFollowee_FolloweeId(@Param("userId")int userId, @Param("targetUserId")int targetUserId);

    @Query("SELECT COUNT(*) FROM Follow f " +
            "WHERE f.followee.userId = :userId ")
    int countByFollower(@Param("userId") int userId);

    @Query("SELECT COUNT(*) FROM Follow f " +
            "WHERE f.follower.userId = :userId ")
    int countByFollowee(@Param("userId") int userId);


    @Query("select f.followee.userId from Follow f where f.follower.userId = :userId")
    List<Integer> findFolloweeIdsByFollowerId(@Param("userId") int userId);

    Optional<Follow> findByFollower_UserIdAndFollowee_UserId(int followerId, int followeeId);
}

