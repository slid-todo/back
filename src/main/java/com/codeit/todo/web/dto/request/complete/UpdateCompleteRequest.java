package com.codeit.todo.web.dto.request.complete;

import lombok.Builder;

@Builder
public record UpdateCompleteRequest(
        String completePicBase64,
        String completePicName,
        String note,
        String completeLink,
        String completeFileBase64,
        String completeFileName

) {
}
