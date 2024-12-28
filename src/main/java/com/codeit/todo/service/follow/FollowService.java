package com.codeit.todo.service.follow;

import com.codeit.todo.web.dto.request.follow.ReadFollowRequest;
import com.codeit.todo.web.dto.response.follow.ReadFollowResponse;
import org.springframework.data.domain.Slice;

public interface FollowService {
    Slice<ReadFollowResponse> readFollows(int userId, ReadFollowRequest request);
}
