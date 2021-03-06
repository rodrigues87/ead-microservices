package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class)
                                                       UserDto userDto) {

        log.debug("POST registerUser userDto received: {}", userDto.toString());

        if (userService.existsByUsername(userDto.getUsername())) {
            log.warn("Nome de usuário já está sendo usado: {}", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de usuário já está sendo usado");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            log.warn("Email de usuário já está sendo usado: {}", userDto.getEmail());

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email de usuário já está sendo usado");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        log.debug("POST registerUser userDto saved: {}", userModel.toString());
        log.info("Usuário salvo com sucesso: {}", userModel.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));

    }


}
