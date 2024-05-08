package com.inai.todolist.models;

import lombok.Data;

@Data
public class AddTaskResponseDto {

    private String taskName;

    private String description;

    private String priority;

    private String deadLine;

    private String userId;

    private String status;

    private Integer taskId;


}
