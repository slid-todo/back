package com.codeit.todo.service.complete;

import com.codeit.todo.web.dto.request.complete.UpdateCompleteRequest;
import com.codeit.todo.web.dto.response.complete.ReadCompleteDetailResponse;
import com.codeit.todo.web.dto.response.complete.UpdateCompleteResponse;

public interface CompleteService {
    UpdateCompleteResponse updateCompleteInfo(int userId, int completeId, UpdateCompleteRequest request);

    ReadCompleteDetailResponse readComplete(int completeId, int userId);
}
