package com.ead.authuser.controllers;


import com.ead.authuser.dtos.*;
import com.ead.authuser.enums.*;
import com.ead.authuser.models.*;
import com.ead.authuser.services.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    UserService userService;

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto){
        Optional<UserModel> userModelOptional = userService.findById(instructorDto.getId());

        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }else {
            var userModel = userModelOptional.get();
            userModel.setUserType(UserType.INSTRUCTOR);
            userService.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

}
