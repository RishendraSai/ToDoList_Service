package com.inai.todolist.models;

import lombok.Data;

@Data
public class TaskSearchDto {
    private String userId;
    private String taskId;
    private String priority;
    private String dueDateCrossed;
    private String status;
    private Integer pageNo;
    private Integer fetchCount;

}
