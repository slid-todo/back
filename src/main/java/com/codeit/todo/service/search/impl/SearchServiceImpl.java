package com.codeit.todo.service.search.impl;

import com.codeit.todo.common.exception.EntityNotFoundException;
import com.codeit.todo.common.exception.goal.GoalNotFoundException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.codeit.todo.common.exception.search.SearchException;
import com.codeit.todo.common.exception.user.UserNotFoundException;
import com.codeit.todo.domain.Goal;
import com.codeit.todo.domain.User;
import com.codeit.todo.domain.enums.SearchField;
import com.codeit.todo.repository.GoalRepository;
import com.codeit.todo.repository.UserRepository;
import com.codeit.todo.service.search.SearchService;
import com.codeit.todo.web.dto.request.search.ReadSearchRequest;
import com.codeit.todo.web.dto.response.goal.ReadGoalSearchResponse;
import com.codeit.todo.web.dto.response.search.ReadSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SearchServiceImpl implements SearchService {
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    private static final int BAD_REQUEST = 400;

    private static final int NOT_FOUND = 404;

    @Override
    public List<ReadSearchResponse> findUserAndGoal(int userId, ReadSearchRequest request) {
        String searchField = request.searchField();
        String keyword = request.keyword();

        if(!Objects.nonNull(keyword) || keyword.isEmpty()){
            throw new SearchException(ErrorStatus.toErrorStatus("검색어가 입력되지 않았습니다.", BAD_REQUEST));
        }

        log.info("Starting search for field: {} with keyword: {}", searchField, keyword);

        if( searchField.equals(SearchField.USER_NAME.getValue()) ){
            List<User> searchedUsers = userRepository.findByNameContains(keyword);
            if(searchedUsers.isEmpty()) throw new SearchException(ErrorStatus.toErrorStatus("유저에 대한 검색 결과가 없습니다.", NOT_FOUND));

            List<ReadSearchResponse> responses = searchedUsers.stream()
                    .map(user -> {
                        List<Goal> goals = user.getGoals();
                        List<ReadGoalSearchResponse> goalsResponses = goals.stream()
                                .map(ReadGoalSearchResponse::from)
                                .toList();

                        return ReadSearchResponse.from(user, goalsResponses);
                    }).toList();
            return responses;

        }else if(searchField.equals(SearchField.GOAL_TITLE.getValue())){
            List<Goal> searchedGoals = goalRepository.findByGoalTitleContains(keyword);
            if(searchedGoals.isEmpty()) throw new SearchException(ErrorStatus.toErrorStatus("목표에 대한 검색 결과가 없습니다.", NOT_FOUND));

            Map<User, List<Goal>> userGoalsMap = searchedGoals.stream()
                    .collect(Collectors.groupingBy(Goal::getUser));

            List<ReadSearchResponse> responses = userGoalsMap.entrySet().stream()
                    .map(entry -> {
                        User user = entry.getKey();
                        List<Goal> goals = entry.getValue();

                        List<ReadGoalSearchResponse> goalsResponses = goals.stream()
                                .map(ReadGoalSearchResponse::from)
                                .toList();

                        return ReadSearchResponse.from(user, goalsResponses);

                    }).toList();
            return responses;
        }else{
            throw new SearchException(ErrorStatus.toErrorStatus("해당 필드로는 검색할 수 없습니다", BAD_REQUEST));
        }
    }
}
