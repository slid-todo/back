package com.codeit.todo.service.likes.impl;

import com.codeit.todo.common.exception.auth.AuthorizationDeniedException;
import com.codeit.todo.common.exception.complete.CompleteNotFoundException;
import com.codeit.todo.common.exception.likes.LikesNotFoundException;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.domain.Likes;
import com.codeit.todo.domain.User;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.LikesRepository;
import com.codeit.todo.repository.UserRepository;
import com.codeit.todo.service.likes.LikesService;
import com.codeit.todo.web.dto.response.likes.CreateLikeResponse;
import com.codeit.todo.web.dto.response.likes.DeleteLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesServiceImpl implements LikesService {

    private final UserRepository userRepository;
    private final CompleteRepository completeRepository;
    private final LikesRepository likesRepository;

    @Override
    public CreateLikeResponse createLikes(int userId, int completeId) {
        if (likesRepository.existsByUser_UserIdAndComplete_CompleteId(userId, completeId)) {
            throw new AuthorizationDeniedException("이미 좋아요를 눌렀습니다.");
        }

        Complete complete = getComplete(completeId);

        if (complete.getTodo().getGoal().getUser().getUserId() == userId) {
            throw new AuthorizationDeniedException("자신의 게시글에 좋아요를 누를 수 없습니다.");
        }

        User user = getUser(userId);
        Likes likes = Likes.toEntity(user, complete);
        Likes createdLikes = likesRepository.save(likes);

        return CreateLikeResponse.fromEntity(createdLikes);
    }

    @Override
    public DeleteLikeResponse deleteLikes(int userId, int completeId) {
        Complete complete = getComplete(completeId);
        User user = getUser(userId);

        Likes likes = likesRepository.findByComplete_CompleteIdAndUser_UserId(complete.getCompleteId(), user.getUserId())
                        .orElseThrow(() -> new LikesNotFoundException("좋아요를 누른 기록이 없습니다."));
        likesRepository.delete(likes);

        return DeleteLikeResponse.fromEntity(complete);
    }

    private Complete getComplete(int completeId) {
        return completeRepository.findById(completeId)
                .orElseThrow(() -> new CompleteNotFoundException(String.valueOf(completeId)));
    }

    private User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId), "User"));
    }
}
