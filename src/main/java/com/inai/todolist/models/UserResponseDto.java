package com.inai.todolist.models;


import lombok.Data;
import java.sql.Timestamp;

@Data
public class UserResponseDto {

    private Integer userId;

    private String name;

    private String phone;

    private String status;

    private String password;

}
