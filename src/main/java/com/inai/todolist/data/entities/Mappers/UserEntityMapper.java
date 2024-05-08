package com.inai.todolist.data.entities.Mappers;

import com.inai.todolist.data.entities.UserEntity;
import com.inai.todolist.models.AddUserDto;
import com.inai.todolist.models.UserResponseDto;
import org.bouncycastle.util.Times;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.xml.transform.Source;
import java.sql.Timestamp;

@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE  = Mappers.getMapper(UserEntityMapper.class);

    @Mapping(source = "addUserDto.userName", target  = "name")
    @Mapping(source = "addUserDto.mobile", target  = "phone")
    UserEntity toEntity(AddUserDto addUserDto, String status, Timestamp createdAt, Timestamp updatedAt);

    @Mapping(source = "id", target  = "userId")
    UserResponseDto toDto(UserEntity user);

}
