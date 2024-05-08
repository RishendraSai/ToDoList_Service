package com.inai.todolist.servcie;

import com.inai.todolist.api.models.TasksSearchResponse;
import com.inai.todolist.models.AddTaskRequestDto;
import com.inai.todolist.models.AddTaskResponseDto;
import com.inai.todolist.models.TaskSearchDto;
import com.inai.todolist.models.UpdateTaskDetailsDto;

public interface TaskDetailsService {

    AddTaskResponseDto addTask(AddTaskRequestDto addTaskRequestDto);

    AddTaskResponseDto updateTaskDetails(UpdateTaskDetailsDto updateTaskDetailsDto);

    TasksSearchResponse SearchTaskDetails(TaskSearchDto taskSearchDto);




}
