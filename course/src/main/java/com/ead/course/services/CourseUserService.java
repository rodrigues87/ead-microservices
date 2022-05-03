package com.ead.course.services;

import com.ead.course.clients.*;
import com.ead.course.ferramentas.*;
import com.ead.course.models.CourseUserModel;
import com.ead.course.repositories.CourseUserRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.*;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseUserService extends AbstractRelatedService<CourseUserModel> {

    private final CourseUserRepository courseUserRepository;
    private final CourseService courseService;

    private final AuthUserClient authUserClient;



    @Override
    public void delete(UUID id) throws ObjectNotFoundException {

    }

    @Override
    public CourseUserModel findById(UUID id) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void deleteAll(Set models) {

    }

    @Override
    public CourseUserModel update(CourseUserModel obj) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public CourseUserModel save(CourseUserModel obj) throws ObjectNotFoundException {
        return courseUserRepository.save(obj);
    }

    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {

        courseService.findById(courseId);

        if(authUserClient.findUserById(userId).getBody() ==null){
            throw new RuntimeException(Constantes.USUARIO_NAO_ENCONTRADO);

        }

        return courseUserRepository.existsByCourseIdAndUserId(courseId,userId);
    }


}
