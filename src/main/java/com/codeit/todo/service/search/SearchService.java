package com.codeit.todo.service.search;

import com.codeit.todo.web.dto.request.search.ReadSearchRequest;
import com.codeit.todo.web.dto.response.search.ReadSearchResponse;

import java.util.List;

public interface SearchService {
    List<ReadSearchResponse> findUserAndGoal(int userId, ReadSearchRequest request);
}
