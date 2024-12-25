package com.codeit.todo.web.dto.request.search;

import jakarta.validation.constraints.NotNull;

public record ReadSearchRequest(
        @NotNull
        String searchField,

        @NotNull
        String keyword
) {

}
