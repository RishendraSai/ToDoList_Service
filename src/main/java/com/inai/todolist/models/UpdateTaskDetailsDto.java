package com.inai.todolist.models;

import com.inai.todolist.common.enums.TaskStatus;
import lombok.Data;


import java.sql.Timestamp;

@Data
public class UpdateTaskDetailsDto {

    private String taskName;
    private String taskId;
    private String userId;
    private String description;
    private String dueDate;
    private String priority;
    private TaskStatus status;

}
