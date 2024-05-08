package com.inai.todolist.servcie;

import com.inai.todolist.models.AddUserDto;
import com.inai.todolist.models.UserResponseDto;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserDetailsService {


    public UserResponseDto addUser(AddUserDto addUserDto);
}
