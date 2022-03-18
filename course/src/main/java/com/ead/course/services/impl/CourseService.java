package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.repositories.CourseRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService extends AbstractService<CourseModel> {

    private final CourseRepository courseRepository;

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public CourseModel save(CourseModel obj) throws ObjectNotFoundException {
        return courseRepository.save(obj);
    }

    @Override
    public CourseModel update(CourseModel obj) throws ObjectNotFoundException {
        verifyIfExists(obj);

        return courseRepository.save(obj);
    }

    @Override
    public void delete(UUID id) throws ObjectNotFoundException {
        findById(id);
        courseRepository.deleteById(id);
    }

    @Override
    public CourseModel findById(UUID id) throws ObjectNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado "));
    }
}
