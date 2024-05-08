package com.inai.todolist.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddTaskRequestDto {


    private String taskName;

    private String description;

    private String priority;

    private String deadLine;

    private String userId;
}
