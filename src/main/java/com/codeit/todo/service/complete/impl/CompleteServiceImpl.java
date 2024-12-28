package com.codeit.todo.service.complete.impl;

import com.codeit.todo.common.exception.complete.CompleteNotFoundException;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.repository.LikesRepository;
import com.codeit.todo.service.complete.CompleteService;
import com.codeit.todo.service.storage.StorageService;
import com.codeit.todo.web.dto.request.complete.UpdateCompleteRequest;
import com.codeit.todo.web.dto.response.comment.ReadCommentResponse;
import com.codeit.todo.web.dto.response.complete.ReadCompleteDetailResponse;
import com.codeit.todo.web.dto.response.complete.UpdateCompleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CompleteServiceImpl implements CompleteService {

    private final StorageService storageService;

    private final CompleteRepository completeRepository;
    private final LikesRepository likesRepository;

    @Override
    public UpdateCompleteResponse updateCompleteInfo(int userId, int completeId, UpdateCompleteRequest request) {
        Complete complete = getComplete(userId, completeId);

        String completePicUrl = "";

        if (Objects.nonNull(request.completePicBase64()) && !request.completePicBase64().isEmpty()) {
            completePicUrl = storageService.uploadFile(request.completePicBase64(), request.completePicName());
        }

        complete.update(completePicUrl, request.completeLink(), request.note());

        return new UpdateCompleteResponse(completeId);
    }

    @Transactional(readOnly = true)
    @Override
    public ReadCompleteDetailResponse readComplete(int completeId, int userId) {
        Complete complete = completeRepository.findById(completeId)
                .orElseThrow(() -> new CompleteNotFoundException(String.valueOf(completeId)));
        List<ReadCommentResponse> commentResponses = complete.getComments().stream()
                .map(ReadCommentResponse::fromEntity)
                .toList();

        Boolean likeStatus = likesRepository.existsByUser_UserIdAndComplete_CompleteId(userId, completeId);

        return ReadCompleteDetailResponse.from(complete, commentResponses, likeStatus);
    }

    private Complete getComplete(int userId, int completeId) {
        return completeRepository.findById(completeId)
                .filter(c -> c.getTodo().getGoal().getUser().getUserId() == userId)
                .orElseThrow(() -> new CompleteNotFoundException(String.valueOf(completeId)));
    }
}
