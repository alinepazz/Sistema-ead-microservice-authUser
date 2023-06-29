package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.models.enums.UserStatus;
import com.ead.authuser.models.enums.UserType;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object>registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                                  @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto){
        log.debug("POST registerUser - userDto received {} ", userDto.toString());
        if (userService.existsByUserName(userDto.getUserName())){
            log.warn("Username {} is Already taken ", userDto.getUserName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already taken!");
        }
        if (userService.existsByEmail(userDto.getEmail())){
            log.warn("Email {} is Already taken ", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already taken!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userService.saveUser(userModel);
        log.debug("POST registerUser - userId saved {} ", userModel.getUserId());
        log.info("User saved successfully userId {} ", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}