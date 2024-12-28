package com.codeit.todo.service.follow.impl;

import com.codeit.todo.common.exception.auth.AuthorizationDeniedException;
import com.codeit.todo.common.exception.follow.FollowNotFoundException;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.Follow;
import com.codeit.todo.domain.User;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.FollowRepository;
import com.codeit.todo.repository.LikesRepository;
import com.codeit.todo.repository.UserRepository;
import com.codeit.todo.service.follow.FollowService;
import com.codeit.todo.web.dto.request.follow.ReadFollowRequest;
import com.codeit.todo.web.dto.response.follow.CreateFollowResponse;
import com.codeit.todo.web.dto.response.follow.DeleteFollowResponse;
import com.codeit.todo.web.dto.response.follow.ReadFollowResponse;
import com.codeit.todo.web.dto.response.slice.CustomSlice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final CompleteRepository completeRepository;
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Slice<ReadFollowResponse> readFollows(int userId, ReadFollowRequest request) {
        List<Integer> followeeIds = followRepository.findFolloweeIdsByFollowerId(userId);

        Pageable pageable = PageRequest.of(0, request.size());

        Slice<Complete> completes;
        if (Objects.isNull(request.lastCompleteId()) || request.lastCompleteId() <= 0) {
            completes = completeRepository.findByFollowees(followeeIds, pageable);
        } else {
            completes = completeRepository.findByFolloweesAfterCompleteId(followeeIds, request.lastCompleteId(), pageable);
        }

        List<ReadFollowResponse> responses = completes.stream()
                .map(complete -> {
                    Boolean likeStatus = likesRepository.existsByUser_UserIdAndComplete_CompleteId(userId, complete.getCompleteId());

                    return ReadFollowResponse.from(complete, likeStatus);
                })
                .toList();

        Integer nextCursor = completes.hasNext()
                ? completes.getContent().get(completes.getContent().size() - 1).getCompleteId()
                : null;

        return new CustomSlice<>(responses, pageable, completes.hasNext(), nextCursor);
    }

    @Override
    public CreateFollowResponse registerFollow(int followerId, int followeeId) {
        if (followRepository.existsByFollower_FollowerIdAndFollowee_FolloweeId(followerId, followeeId)) {
            throw new AuthorizationDeniedException("이미 팔로우로 등록한 회원입니다.");
        }

        User follower = getUser(followerId);
        User followee = getUser(followeeId);

        Follow follow = Follow.from(follower, followee);
        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.fromEntity(savedFollow);
    }

    @Override
    public DeleteFollowResponse cancelFollow(int followerId, int followeeId) {
        if (!followRepository.existsByFollower_FollowerIdAndFollowee_FolloweeId(followerId, followeeId)) {
            throw new AuthorizationDeniedException("팔로우 내역이 존재하지 않습니다.");
        }

        Follow follow = followRepository.findByFollower_UserIdAndFollowee_UserId(followerId, followeeId)
                .orElseThrow(() -> new FollowNotFoundException("FollowerId : " + followerId + ", FolloweeId : " + followeeId));

        followRepository.delete(follow);

        return DeleteFollowResponse.from(followerId, followeeId);
    }

    private User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId), "User"));
    }
}
