package com.inai.todolist.servcie.impl;

import com.inai.todolist.common.Utils.FieldValidationStatus;
import com.inai.todolist.common.enums.UserStatusType;
import com.inai.todolist.common.exceptions.DataConflictException;
import com.inai.todolist.data.entities.Mappers.UserEntityMapper;
import com.inai.todolist.data.entities.UserEntity;
import com.inai.todolist.data.repositories.UserRepository;
import com.inai.todolist.models.AddUserDto;
import com.inai.todolist.models.UserResponseDto;
import com.inai.todolist.servcie.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserDetailsServcieImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserResponseDto addUser(AddUserDto addUserDto) {
        UserResponseDto userResponseDto = null;
        Optional<UserEntity> user = userRepository.findByMobile(addUserDto.getMobile());
        if(!user.isPresent()){
            addUserDto.setPassword("Password");
            UserEntity newUser = userRepository.save(UserEntityMapper.INSTANCE.toEntity(addUserDto,
                    UserStatusType.ACTIVE.toString(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));

            userResponseDto =  UserEntityMapper.INSTANCE.toDto(newUser);

        }
        else {
            throw new DataConflictException(FieldValidationStatus.USER_ALREADY_EXISTS_CODE,
                    FieldValidationStatus.USER_ALREADY_EXISTS_MSG);
        }
        return userResponseDto;
    }
}
