package com.inai.todolist.controller.tasks;

import com.inai.todolist.api.TasksApi;
import com.inai.todolist.api.models.*;
import com.inai.todolist.controller.tasks.mappers.TaskDetailsMapper;
import com.inai.todolist.controller.tasks.validators.TaskDetailsValidator;
import com.inai.todolist.models.AddTaskRequestDto;
import com.inai.todolist.models.AddTaskResponseDto;
import com.inai.todolist.models.TaskSearchDto;
import com.inai.todolist.models.UpdateTaskDetailsDto;
import com.inai.todolist.servcie.impl.TaskDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController implements TasksApi {

    @Autowired
    private TaskDetailsValidator taskDetailsValidator;

    @Autowired
    private TaskDetailsServiceImpl taskDetailsService;

    private final Logger logger = LogManager.getLogger(TaskController.class);


    @Override
    public ResponseEntity<AddTaskResponse> addTask(AddTaskRequest addTaskRequest) {
        taskDetailsValidator.AddTaskDetailsValidator(addTaskRequest);
        logger.info("Add New Task Details validated for user={}", addTaskRequest.getUserId());
        AddTaskRequestDto addTaskRequestDto = TaskDetailsMapper.INSTANCE.toDto(addTaskRequest);
        AddTaskResponseDto addTaskResponseDto = taskDetailsService.addTask(addTaskRequestDto);
        AddTaskResponse addTaskResponse = TaskDetailsMapper.INSTANCE.fromDto(addTaskResponseDto);
        return  new ResponseEntity<>(addTaskResponse, HttpStatus.OK);

    }


    @Override
    public ResponseEntity<UpdateTaskResponse> updateTaskDetails(String taskId, UpdateTaskRequest updateTaskRequest) {
        taskDetailsValidator.UpdateTaskDetailsValidator(updateTaskRequest, taskId);
        logger.info("Update Task Details validated for user={} with taskId={}", updateTaskRequest.getUserId(), taskId);
        UpdateTaskDetailsDto updateTaskDetailsDto = TaskDetailsMapper.INSTANCE.toDto(updateTaskRequest,taskId);
        AddTaskResponseDto addTaskResponseDto = taskDetailsService.updateTaskDetails(updateTaskDetailsDto);
        UpdateTaskResponse updateTaskResponse = TaskDetailsMapper.INSTANCE.toResponse(addTaskResponseDto);
        return new ResponseEntity<>(updateTaskResponse, HttpStatus.OK);

    }


    @Override
    public ResponseEntity<TasksSearchResponse> getTaskDetails(String userId, String taskId,
                                                              String dueDateCrossed, String priority,
                                                              String pageNo, String fetchCount, String status) {
        taskDetailsValidator.validateSearchDetails(userId,taskId,dueDateCrossed,priority,status);
        TaskSearchDto taskSearchDto = TaskDetailsMapper.INSTANCE.toDto(userId,taskId,dueDateCrossed,priority);
        TasksSearchResponse tasksSearchResponse = taskDetailsService.SearchTaskDetails(taskSearchDto);
        return new ResponseEntity<>(tasksSearchResponse, HttpStatus.OK);
    }
}
