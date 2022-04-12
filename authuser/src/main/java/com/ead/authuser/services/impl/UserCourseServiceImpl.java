package com.ead.authuser.services.impl;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserClient userClient;

    @Override
    public UserCourseModel saveSubscriptionUserInCourse(UUID userId, UUID courseId) {
        UserCourseModel userCourseModel = new UserCourseModel();

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if(userModelOptional.isEmpty()){
            throw new RuntimeException("O usuário não existe");
        }

        CourseDto courseDto = userClient.findCourseById(userId);

        if(courseDto ==null){
            throw new RuntimeException("O Curso não existe");
        }

        userCourseModel.setUser(userModelOptional.get());
        userCourseModel.setCourseId(courseId);

        return userCourseRepository.save(userCourseModel);
    }
}
