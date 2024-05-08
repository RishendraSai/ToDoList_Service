package com.inai.todolist.controller.tasks.mappers;

import com.inai.todolist.api.models.*;
import com.inai.todolist.common.Utils.DateTimeUtil;
import com.inai.todolist.models.AddTaskRequestDto;
import com.inai.todolist.models.AddTaskResponseDto;
import com.inai.todolist.models.TaskSearchDto;
import com.inai.todolist.models.UpdateTaskDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.time.OffsetDateTime;
import java.util.Optional;

@Mapper
public interface TaskDetailsMapper {

    TaskDetailsMapper INSTANCE = Mappers.getMapper(TaskDetailsMapper.class);

    AddTaskRequestDto toDto(AddTaskRequest addTaskRequest);

 //   @Mapping(target = "dueDate", source = "updateTaskRequest.deadLine", qualifiedByName = "strToTimeStamp")
    UpdateTaskDetailsDto toDto(UpdateTaskRequest updateTaskRequest,String taskId);

    AddTaskResponse fromDto(AddTaskResponseDto addTaskResponseDto);

    UpdateTaskResponse toResponse(AddTaskResponseDto addTaskResponseDto);

//    @Named("strToTimeStamp")
//    static OffsetDateTime dateTimeMapperToStr(String timestamp) {
//        return Optional.ofNullable(timestamp).map(value -> DateTimeUtil.toDateTime(timestamp)).orElse(null);
//    }
//
//    @Named("dateTimeMapperToStr")
//    static String dateTimeMapperToStr(OffsetDateTime dateTime) {
//        return Optional.ofNullable(dateTime).map(value -> DateTimeUtil.toString(dateTime)).orElse("");
//    }

    TaskSearchDto toDto(String userId, String taskId, String dueDateCrossed, String priority);



}
