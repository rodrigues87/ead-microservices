package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.services.UserCourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    CourseClient userClient;

    @Autowired
    UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByuser
            (@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
             @RequestParam(required = false) UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId,pageable));

    }

    @GetMapping("/user/{userId}/{courseId}")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID userId, UUID courseId){

        UserCourseModel userCourseModel = userCourseService.saveSubscriptionUserInCourse(userId,courseId);

        return ResponseEntity.status(HttpStatus.OK).body(userCourseModel);
    }
}
