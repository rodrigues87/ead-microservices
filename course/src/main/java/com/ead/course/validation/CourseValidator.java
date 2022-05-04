package com.ead.course.validation;

import com.ead.course.clients.*;
import com.ead.course.dtos.*;
import com.ead.course.enums.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.client.*;

import java.util.*;

@Component
public class CourseValidator implements Validator {

    @Autowired private Validator validator;
    @Autowired private AuthUserClient authUserClient;


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object object, Errors errors) {

        CourseDto courseDto = (CourseDto) object;
        validator.validate(courseDto, errors);
        if(!errors.hasErrors()){
            this.validadeUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validadeUserInstructor(UUID userInstructorId, Errors errors){
        ResponseEntity<UserDto> userDtoResponseEntity = null;
        try {
            authUserClient.findUserById(userInstructorId);
            if(userDtoResponseEntity.getBody().getUserType().equals(UserType.STUDENT)){
                errors.rejectValue("userInstructor", "UserInstructorError", "Estudantes não podem criar novos cursos");
            }
        }catch (HttpStatusCodeException e){

            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                errors.rejectValue("userInstructor", "UserInstructorError", "Instrutor não encontrado");
            }
        }

    }
}
