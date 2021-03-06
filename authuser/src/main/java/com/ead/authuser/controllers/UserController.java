package com.ead.authuser.controllers;


import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId",
                                                               direction = Sort.Direction.ASC) Pageable pageable,
                                                       @RequestParam(required = false) UUID courseId) {

        Page<UserModel> userModelPage = null;
        if(courseId !=null){
            userModelPage = userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        }else {
            userModelPage = userService.findAll(spec, pageable);
        }

        if (!userModelPage.isEmpty()) {
            for (UserModel userModel : userModelPage.getContent()) {
                userModel.add(linkTo(methodOn(UserController.class).findUserById(userModel.getUserId())).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findUserById(@PathVariable(value = "userId") UUID userId) {

        Optional<UserModel> userModel = userService.findById(userId);
        if (userModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userModel.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");


    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {

        Optional<UserModel> userModel = userService.findById(userId);
        if (userModel.isPresent()) {
            userService.delete(userModel.get());
            return ResponseEntity.status(HttpStatus.OK).body("Usu??rio deletado com sucesso");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody
                                             @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {

        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (userModelOptional.isPresent()) {
            var userModel = userModelOptional.get();
            userModel.setFullName(userDto.getFullName());
            userModel.setPhoneNumber(userDto.getPhoneNumber());
            userModel.setCpf(userDto.getCpf());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody
                                                 @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");
        }
        if (!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
            log.warn("tentativa de verificar senha {}", userDto.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senhas n??o conferem");

        }

        var userModel = userModelOptional.get();
        userModel.setPassword(userDto.getPassword());

        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody
                                              @Validated({UserDto.UserView.ImagePut.class})
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");
        }

        var userModel = userModelOptional.get();
        userModel.setImageUrl(userDto.getImageUrl());

        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }
}
