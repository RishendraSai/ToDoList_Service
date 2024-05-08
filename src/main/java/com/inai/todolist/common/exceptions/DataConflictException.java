package com.inai.todolist.common.exceptions;

import lombok.Getter;

@Getter
public class DataConflictException extends  RuntimeException{
    private String code;

    private String message;

    public DataConflictException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}

