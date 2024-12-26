package com.codeit.todo.domain.enums;

import lombok.Getter;

@Getter
public enum SearchField {
    USER_NAME("유저명"),
    GOAL_TITLE("목표명");

    private final String value;

    SearchField(String value){
        this.value = value;
    }

}
