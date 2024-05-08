package com.inai.todolist.controller.users.mappers;

import com.inai.todolist.api.models.AddUser;
import com.inai.todolist.api.models.UserResponse;
import com.inai.todolist.models.AddUserDto;
import com.inai.todolist.models.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AddUserDto toDto(AddUser user);

    UserResponse toResponse(UserResponseDto userResponseDto);
}
