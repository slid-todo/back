package com.codeit.todo.service.complete.impl;

import com.codeit.todo.common.exception.complete.CompleteNotFoundException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.codeit.todo.domain.Complete;
import com.codeit.todo.repository.CompleteRepository;
import com.codeit.todo.service.complete.CompleteService;
import com.codeit.todo.service.storage.StorageService;
import com.codeit.todo.web.dto.request.complete.UpdateCompleteRequest;
import com.codeit.todo.web.dto.response.complete.UpdateCompleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CompleteServiceImpl implements CompleteService {

    private final StorageService storageService;

    private final CompleteRepository completeRepository;

    @Override
    public UpdateCompleteResponse updateCompleteInfo(int userId, int completeId, UpdateCompleteRequest request) {
        Complete complete = completeRepository.findById(completeId)
                .filter(c -> c.getTodo().getGoal().getUser().getUserId() == userId)
                .orElseThrow(() -> new CompleteNotFoundException(String.valueOf(completeId)));

        String completePicUrl = "";
        String completeFileUrl = "";

        if (Objects.nonNull(request.completePicBase64()) && !request.completePicBase64().isEmpty()) {
            completePicUrl = storageService.uploadFile(request.completePicBase64(), request.completePicName());
        }

        if (Objects.nonNull(request.completeFileBase64()) && !request.completeFileBase64().isEmpty()) {
            completeFileUrl = storageService.uploadFile(request.completeFileBase64(), request.completeFileName());
        }

        complete.update(completePicUrl, completeFileUrl, request.completeLink(), request.note());

        return new UpdateCompleteResponse(completeId);
    }
}
