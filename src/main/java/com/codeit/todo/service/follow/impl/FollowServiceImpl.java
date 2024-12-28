package com.codeit.todo.service.follow.impl;

import com.codeit.todo.domain.Complete;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.FollowRepository;
import com.codeit.todo.repository.LikesRepository;
import com.codeit.todo.service.follow.FollowService;
import com.codeit.todo.web.dto.request.follow.ReadFollowRequest;
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
}
