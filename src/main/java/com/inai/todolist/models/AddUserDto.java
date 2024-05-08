package com.inai.todolist.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddUserDto {

    private String userName;

    private String mobile;

    private String password;



}
