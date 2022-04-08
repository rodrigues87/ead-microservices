package com.ead.course.services;

import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.specifications.SpecificationTemplate;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseUserService extends AbstractRelatedService<CourseUserModel> {

    private final CourseUserRepository courseRepository;

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
        return null;
    }
}
