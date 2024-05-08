package com.inai.todolist.servcie.impl;

import com.inai.todolist.api.models.AddTaskResponse;
import com.inai.todolist.api.models.TaskResponse;
import com.inai.todolist.api.models.TasksSearchResponse;
import com.inai.todolist.common.Utils.DateTimeUtil;
import com.inai.todolist.common.Utils.FieldValidationStatus;
import com.inai.todolist.common.enums.UserStatusType;
import com.inai.todolist.common.exceptions.DataConflictException;
import com.inai.todolist.controller.tasks.TaskController;
import com.inai.todolist.data.entities.Mappers.TaskDetailsEntityMapper;
import com.inai.todolist.data.entities.TaskDetailsEntity;
import com.inai.todolist.data.entities.UserEntity;
import com.inai.todolist.data.repositories.TaskDetailRepository;
import com.inai.todolist.data.repositories.UserRepository;
import com.inai.todolist.data.specifications.TaskDetailsSpecification;
import com.inai.todolist.models.AddTaskRequestDto;
import com.inai.todolist.models.AddTaskResponseDto;
import com.inai.todolist.models.TaskSearchDto;
import com.inai.todolist.models.UpdateTaskDetailsDto;
import com.inai.todolist.servcie.TaskDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.apache.bcel.generic.TABLESWITCH;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PageRanges;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;


@Service
public class TaskDetailsServiceImpl implements TaskDetailsService {

    public final Logger logger = LogManager.getLogger(TaskDetailsServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskDetailRepository taskDetailRepository;
    @Override
    public AddTaskResponseDto addTask(AddTaskRequestDto addTaskRequestDto) {

        Optional<UserEntity> userEntity = userRepository.findById(Integer.valueOf(addTaskRequestDto.getUserId()));
        AddTaskResponseDto addTaskRequestDto1 = null;
        if(userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            logger.info("Adding new task for the UserId={}",addTaskRequestDto.getUserId());
            if (user.getStatus().equals(UserStatusType.ACTIVE.toString())) {
                TaskDetailsEntity taskDetailsEntity = taskDetailRepository.save(TaskDetailsEntityMapper.INSTANCE.toEntity(addTaskRequestDto,
                        new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()),
                        convertToTimeStamp(addTaskRequestDto.getDeadLine()), String.valueOf(user.getId())));
                addTaskRequestDto1 = TaskDetailsEntityMapper.INSTANCE.fromEntity(taskDetailsEntity);


            }
           // logger.info("Added new task for the UserId={} with taskId={}",addTaskRequestDto.getUserId(), taskDetailsEntity.getId());
            return addTaskRequestDto1;
        }
        else {
            throw new DataConflictException(FieldValidationStatus.USER_NOT_EXISTS_CODE, FieldValidationStatus.USER_NOT_EXISTS_MSG);
        }

    }

    @Override
    public AddTaskResponseDto updateTaskDetails(UpdateTaskDetailsDto updateTaskDetailsDto) {

        return userRepository.findById(Integer.valueOf(updateTaskDetailsDto.getUserId()))
                .map(user -> taskDetailRepository.findById((Integer.valueOf(updateTaskDetailsDto.getTaskId())))
                        .map(taskDetailsEntity -> {
                            logger.info("Updating  task for the UserId={} for taskId={}",
                                    updateTaskDetailsDto.getUserId(), updateTaskDetailsDto.getTaskId());
                            if (updateTaskDetailsDto.getTaskName() != null)
                            taskDetailsEntity.setTaskName(updateTaskDetailsDto.getTaskName());
                            if (updateTaskDetailsDto.getDescription() != null)
                                taskDetailsEntity.setDescription(updateTaskDetailsDto.getDescription());
                            if (updateTaskDetailsDto.getPriority() != null)
                                taskDetailsEntity.setDescription(updateTaskDetailsDto.getDescription());
                            if (updateTaskDetailsDto.getDueDate() != null)
                                taskDetailsEntity.setDueDate(convertToTimeStamp(updateTaskDetailsDto.getDueDate()));
                            if(updateTaskDetailsDto.getStatus() != null)
                                taskDetailsEntity.setStatus(updateTaskDetailsDto.getStatus().toString());
                            TaskDetailsEntity updatedTaskEntity = taskDetailRepository.save(taskDetailsEntity);
                            logger.info("Updated task for the UserId={} for taskId={}",
                                    updateTaskDetailsDto.getUserId(), updateTaskDetailsDto.getTaskId());
                            return TaskDetailsEntityMapper.INSTANCE.fromEntity(updatedTaskEntity);

                        }).orElseThrow(() -> {
                            return new DataConflictException(FieldValidationStatus.TASK_ID_INVALID_CODE,
                                    FieldValidationStatus.TASK_ID_INVALID_MSG);
                        })).orElseThrow(
                        () -> {
                            return new DataConflictException(FieldValidationStatus.USER_NOT_EXISTS_CODE,
                                    FieldValidationStatus.USER_NOT_EXISTS_MSG);
                        });

    }

    @Override
    public TasksSearchResponse SearchTaskDetails(TaskSearchDto taskSearchDto) {

        TaskDetailsSpecification taskDetailsSpecification = new TaskDetailsSpecification(taskSearchDto);
        Integer pageNumber= 0;
        if(taskSearchDto.getPageNo()!=null){
            pageNumber=taskSearchDto.getPageNo();
        }
        Pageable page = PageRequest.of(pageNumber,10,
                Sort.by(Sort.Direction.ASC, "id"));
        Page<TaskDetailsEntity> taskDetailsEntities = taskDetailRepository.findAll(taskDetailsSpecification, page);

        if (!taskDetailsEntities.isEmpty() && !CollectionUtils.isEmpty(taskDetailsEntities.toList())) {

            Integer nextPageNumber = null;
            int totalPages = taskDetailsEntities.getTotalPages() - 1;
            if (totalPages > taskDetailsEntities.getNumber()) {
                nextPageNumber = taskDetailsEntities.getNumber() + 1;
            }

            List<TaskResponse> results = taskDetailsEntities.stream()
                    .map(TaskDetailsEntityMapper.INSTANCE::searchTasKDeatails).collect(Collectors.toList());

            TasksSearchResponse tasksSearchResponse = new TasksSearchResponse();
            tasksSearchResponse.setTasks(results);
            tasksSearchResponse.setNextPage(nextPageNumber);
            return tasksSearchResponse;
        } else {
            logger.info("No Tasks found");
            return null;
        }

    }

    private Timestamp convertToTimeStamp(String time) {
        Timestamp time2 = null;
        try {
            time2 = DateTimeUtil.formatFromString(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return time2;
    }


}
