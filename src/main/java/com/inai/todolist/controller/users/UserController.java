package com.inai.todolist.controller.users;

import com.inai.todolist.api.UsersApi;
import com.inai.todolist.api.models.AddUser;
import com.inai.todolist.api.models.UserResponse;
import com.inai.todolist.controller.users.mappers.UserMapper;
import com.inai.todolist.controller.users.validators.UserDetailsValidator;
import com.inai.todolist.models.AddUserDto;
import com.inai.todolist.models.UserResponseDto;
import com.inai.todolist.servcie.impl.UserDetailsServcieImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController implements UsersApi {
    private final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserDetailsValidator userDetailsValidator;

    @Autowired
    private UserDetailsServcieImpl userDetailsServcie;

    @Override
    public ResponseEntity<UserResponse> addUser(AddUser addUser) {

        userDetailsValidator.AddUserDetailsValidator(addUser);
        logger.info("Adding new User to Application with phone={}", addUser.getMobile());
        AddUserDto addUserDto = UserMapper.INSTANCE.toDto(addUser);
        UserResponseDto userResponseDto = userDetailsServcie.addUser(addUserDto);
        UserResponse userResponse = UserMapper.INSTANCE.toResponse(userResponseDto);
        logger.info("Addes new User to Application with phone={} and UserId={}", addUser.getMobile(),
                userResponse.getUserId());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

    @RequestMapping(path = "/get", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String adduserrrs() {
        return "rishi";

    }

}
