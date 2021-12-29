package com.ead.authuser.controllers;


import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){

        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findUserById(@PathVariable (value = "userId") UUID userId){

        Optional<UserModel> userModel = userService.findById(userId);
        if(userModel.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userModel.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable (value = "userId") UUID userId){

        Optional<UserModel> userModel = userService.findById(userId);
        if(userModel.isPresent()){
            userService.delete(userModel.get());
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}
