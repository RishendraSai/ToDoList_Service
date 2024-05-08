package com.inai.todolist.common.Utils;


import com.inai.todolist.common.exceptions.ErrorDetails;

public class CommonUtil {

    public static ErrorDetails createErrorDetails(String errorCode, String field, String message) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setCode(String.valueOf(errorCode));
        errorDetail.setField(field);
        errorDetail.setMessage(message);
        return errorDetail;
    }

}
