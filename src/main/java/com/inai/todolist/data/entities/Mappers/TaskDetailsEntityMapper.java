package com.inai.todolist.data.entities.Mappers;

import com.inai.todolist.api.models.TaskResponse;
import com.inai.todolist.common.Utils.DateTimeUtil;
import com.inai.todolist.data.entities.TaskDetailsEntity;
import com.inai.todolist.data.entities.UserEntity;
import com.inai.todolist.models.AddTaskRequestDto;
import com.inai.todolist.models.AddTaskResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Optional;

@Mapper
public interface TaskDetailsEntityMapper {

    TaskDetailsEntityMapper INSTANCE = Mappers.getMapper(TaskDetailsEntityMapper.class);


    @Mapping(target = "status", constant = "PENDING")

    TaskDetailsEntity toEntity(AddTaskRequestDto addTaskRequestDto,
                              Timestamp createdAt, Timestamp updatedAt, Timestamp dueDate, String userId);

   // @Mapping(source ="taskDetailsEntity.dueDate", target = "deadLine", qualifiedByName= "dateTimeMapperToStr")

    @Mapping(target = "priority", source = "taskDetailsEntity.priority")
    @Mapping(target = "status", source = "taskDetailsEntity.status")
    @Mapping(target = "taskId", source ="taskDetailsEntity.id")
    @Mapping(target = "deadLine", source = "taskDetailsEntity.dueDate")
    AddTaskResponseDto fromEntity(TaskDetailsEntity taskDetailsEntity);


    @Mapping(target = "priority", source = "taskDetailsEntity.priority")
    @Mapping(target = "status", source = "taskDetailsEntity.status")
    @Mapping(target = "taskId", source ="taskDetailsEntity.id")
    @Mapping(target = "deadLine", source = "taskDetailsEntity.dueDate")
    TaskResponse searchTasKDeatails(TaskDetailsEntity taskDetailsEntity);




}
