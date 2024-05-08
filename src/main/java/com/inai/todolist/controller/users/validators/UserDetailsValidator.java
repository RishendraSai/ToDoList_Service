package com.inai.todolist.controller.users.validators;

import com.inai.todolist.api.models.AddUser;
import com.inai.todolist.common.Utils.CommonUtil;
import com.inai.todolist.common.Utils.FieldValidationStatus;
import com.inai.todolist.common.Utils.RegExUtil;
import com.inai.todolist.common.Utils.ToDoListCodeType;
import com.inai.todolist.common.exceptions.ErrorDetails;
import com.inai.todolist.common.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsValidator {

    public void AddUserDetailsValidator(AddUser addUser){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(addUser == null){
            throw new ValidationException(ToDoListCodeType.BAD_REQUEST_CODE,ToDoListCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateName(addUser.getUserName()));
        errorDetails.addAll(validatePhoneNumber(addUser.getMobile()));

        if (errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }

    }
    private  List<ErrorDetails> validateName(String name) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(name)){
            if(!RegExUtil.isAlphaNumericWithSpecialChar(name)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_NAME_INVALID_CODE,
                        "name",FieldValidationStatus.USER_NAME_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_NAME_INVALID_CODE,
                    "name",FieldValidationStatus.USER_NAME_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }

    private   List<ErrorDetails> validatePhoneNumber(String number) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(number)){
            if(!RegExUtil.isNumeric(number) || number.length()!=10){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_PHONE_NUMBER_INVALID_CODE,
                        "phoneNumber",FieldValidationStatus.USER_PHONE_NUMBER_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_PHONE_NUMBER_INVALID_CODE,
                    "phoneNumber",FieldValidationStatus.USER_PHONE_NUMBER_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;

    }

    private  void sendBadRequestResponse(List<ErrorDetails> errorDetails) {
        throw new ValidationException(ToDoListCodeType.BAD_REQUEST_CODE,ToDoListCodeType.BAD_REQUEST_MSG,errorDetails);


    }
}
