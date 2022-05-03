package com.ead.course.controllers;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.*;
import com.ead.course.models.*;
import com.ead.course.services.*;
import javassist.tools.rmi.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    CourseUserService courseUserService;


    @Autowired CourseService courseService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<CourseDto>> getAllUsersByCourse(@PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "courseId") UUID courseId){

        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllCoursesByUser(courseId,pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                           @RequestBody @Valid SubscriptionDto subscriptionDto) throws ObjectNotFoundException {


        if(courseUserService.existsByCourseAndUser(courseId,subscriptionDto.getUserId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O usuário já está cadastrado no curso");

        }

        CourseModel courseModel = courseService.findById(courseId);

        CourseUserModel courseUserModel = new CourseUserModel(null,courseModel,subscriptionDto.getUserId());

        courseUserService.save(courseUserModel);

        authUserClient.subscriptionUserInCourse(courseUserModel , subscriptionDto.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(courseUserModel);

    }
}
