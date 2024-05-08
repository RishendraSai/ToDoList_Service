package com.inai.todolist.controller.tasks.validators;

import com.inai.todolist.api.models.AddTaskRequest;
import com.inai.todolist.api.models.UpdateTaskRequest;
import com.inai.todolist.common.Utils.*;
import com.inai.todolist.common.enums.PriorityEnum;
import com.inai.todolist.common.enums.TaskStatus;
import com.inai.todolist.common.exceptions.ErrorDetails;
import com.inai.todolist.common.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskDetailsValidator {


    public void AddTaskDetailsValidator(AddTaskRequest addTaskRequest){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(addTaskRequest == null){
            throw new ValidationException(ToDoListCodeType.BAD_REQUEST_CODE,ToDoListCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateName(addTaskRequest.getTaskName()));
        errorDetails.addAll(validateUserId(addTaskRequest.getUserId()));
        errorDetails.addAll(validatePriority(addTaskRequest.getPriority()));
        errorDetails.addAll(validateDateTime(addTaskRequest.getDeadLine()));

        if (errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }

    }

    public void UpdateTaskDetailsValidator(UpdateTaskRequest updateTaskRequest, String taskId){

        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(updateTaskRequest == null){
            throw new ValidationException(ToDoListCodeType.BAD_REQUEST_CODE,ToDoListCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateTaskName(updateTaskRequest.getTaskName()));
        errorDetails.addAll(validateUserId(updateTaskRequest.getUserId()));
        errorDetails.addAll(validatePriority(updateTaskRequest.getPriority()));
        errorDetails.addAll(validateDateTime(updateTaskRequest.getDeadLine()));
        errorDetails.addAll(validateTaskId(taskId));
        errorDetails.addAll(validateStatus(updateTaskRequest.getStatus()));


        if (errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }

    }

    public void validateSearchDetails(String userId, String taskId, String dueDateCrossed, String priority, String status) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        errorDetails.addAll(validateUserId(userId));
        errorDetails.addAll(validateSearchPriority(priority));
        errorDetails.addAll(validateDateTime(dueDateCrossed));
        errorDetails.addAll(validateSearchTaskId(taskId));
        errorDetails.addAll(validateStatus(status));

        if (errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }

    }


    private List<ErrorDetails> validateStatus(String status) {
         List<ErrorDetails> errorDetails = new ArrayList<>();
         if (StringUtils.hasText(status)
                 && Arrays.stream(TaskStatus.values()).noneMatch(value -> status.equals(value.name()))) {
             ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.STATUS_INVALID_CODE,
                     "status",FieldValidationStatus.STATUS_INVALID_MSG);
             errorDetails.add(errorDetail);
         }
         return errorDetails;
    }

    private List<ErrorDetails> validatePriority(String priority) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if (!StringUtils.hasText(priority)
                || Arrays.stream(PriorityEnum.values()).noneMatch(value -> priority.equals(value.name()))) {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.PRIORITY_INVALID_CODE,
                    "priority",FieldValidationStatus.PRIORITY_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }

    private List<ErrorDetails> validateSearchPriority(String priority) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if (StringUtils.hasText(priority)
                && Arrays.stream(PriorityEnum.values()).noneMatch(value -> priority.equals(value.name()))) {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.PRIORITY_INVALID_CODE,
                    "priority",FieldValidationStatus.PRIORITY_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }
    private List<ErrorDetails> validateDateTime(String requestDateTime) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(requestDateTime)) {
            boolean isValid = DateTimeUtil.isTimeStampValid(requestDateTime);
            if (!isValid) {
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.DATE_ERROR_CODE,
                        "DateTime", FieldValidationStatus.DATE_ERROR_CODE_MSG);
                errorDetails.add(errorDetail);
            }
        }
        return errorDetails;
    }




    private List<ErrorDetails> validateUserId(String userId) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(userId)) {
            if(!RegExUtil.isNumeric(userId)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_ID_INVALID_CODE,
                        "userId",FieldValidationStatus.USER_ID_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_ID_NOT_EMPTY_CODE,
                    "userId",FieldValidationStatus.USER_ID_NOT_EMPTY_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }

    private List<ErrorDetails> validateTaskId(String taskId) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(taskId)) {
            if(!RegExUtil.isNumeric(taskId)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.TASK_ID_INVALID_CODE1,
                        "taskId",FieldValidationStatus.TASK_ID_INVALID_MSG1);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.TASK_ID_NOT_EMPTY_CODE,
                    "taskId",FieldValidationStatus.TASK_ID_NOT_EMPTY_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }

    private List<ErrorDetails> validateSearchTaskId(String taskId) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(taskId)) {
            if(!RegExUtil.isNumeric(taskId)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_NAME_INVALID_CODE,
                        "taskId",FieldValidationStatus.USER_NAME_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        return errorDetails;
    }
    private  List<ErrorDetails> validateName(String name) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(name)){
            if(!RegExUtil.isAlphaNumericWithSpecialChar(name)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.TASK_NAME_INVALID_CODE,
                        "name",FieldValidationStatus.TASK_NAME_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.TASK_NAME_INVALID_CODE,
                    "name",FieldValidationStatus.TASK_NAME_INVALID_CODE);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }

    private  List<ErrorDetails> validateTaskName(String name) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(name)){
            if(!RegExUtil.isAlphaNumericWithSpecialChar(name)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.TASK_NAME_INVALID_CODE,
                        "name",FieldValidationStatus.TASK_NAME_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        return errorDetails;
    }


    private  void sendBadRequestResponse(List<ErrorDetails> errorDetails) {
        throw new ValidationException(ToDoListCodeType.BAD_REQUEST_CODE,ToDoListCodeType.BAD_REQUEST_MSG,errorDetails);


    }

}
